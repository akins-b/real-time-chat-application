package com.boma.backend.services.impl;

import com.boma.backend.dto.request.QueryFriendRequest;
import com.boma.backend.models.FriendRequest;
import com.boma.backend.models.Users;
import com.boma.backend.repositories.FriendRequestRepo;
import com.boma.backend.repositories.UserRepo;
import com.boma.backend.services.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.boma.backend.enums.FriendRequestStatus.*;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepo friendRequestRepo;
    private final UserRepo userRepo;

    @Override
    public void sendFriendRequest(long senderId, long receiverId) {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(userRepo.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found")));
        friendRequest.setReceiver(userRepo.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found")));
        friendRequest.setStatus(PENDING);

        friendRequestRepo.save(friendRequest);
    }

    @Override
    public void acceptFriendRequest(long requestId) {
        FriendRequest friendRequest = friendRequestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        if (friendRequest.getStatus() != PENDING || friendRequest.getSender() == null || friendRequest.getReceiver() == null) {
            throw new IllegalStateException("Friend request is not valid for acceptance");
        }
        friendRequest.setStatus(ACCEPTED);
        friendRequestRepo.save(friendRequest);

        // Update the users' friends lists
        Users receiver = friendRequest.getReceiver();
        Users sender = friendRequest.getSender();

        // Add each other as friends
        receiver.getFriends().add(sender);
        sender.getFriends().add(receiver);

        // Save the updated users
        userRepo.save(receiver);
        userRepo.save(sender);
    }

    @Override
    public void rejectFriendRequest(long requestId) {
        FriendRequest friendRequest = friendRequestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        if (friendRequest.getStatus() != PENDING) {
            throw new IllegalStateException("Friend request is already processed");
        }

        friendRequestRepo.delete(friendRequest);

    }

    @Override
    public Page<FriendRequest> getPendingRequests(QueryFriendRequest request) {
        Users user = userRepo.findById(request.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<FriendRequest> response = friendRequestRepo.findByReceiverIdAndStatus(
                user.getId(), PENDING, pageable);
        if (response.isEmpty()) {
            throw new IllegalStateException("No pending friend requests found for user");
        } else {
            return response;
        }
    }
}
