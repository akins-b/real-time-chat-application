package com.boma.backend.controllers;

import com.boma.backend.dto.request.QueryFriendRequest;
import com.boma.backend.dto.response.FriendRequestDTO;
import com.boma.backend.models.FriendRequest;
import com.boma.backend.services.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend-requests")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestParam long senderId, @RequestParam long receiverId) {
        friendRequestService.sendFriendRequest(senderId, receiverId);
        return ResponseEntity.ok("Friend request sent successfully");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam long requestId) {
        friendRequestService.acceptFriendRequest(requestId);
        return ResponseEntity.ok("Friend request accepted successfully");
    }

    @DeleteMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam long requestId) {
        friendRequestService.rejectFriendRequest(requestId);
        return ResponseEntity.ok("Friend request rejected successfully");
    }

    @GetMapping("/pending")
    public ResponseEntity<FriendRequestDTO<Page<FriendRequest>>> getPendingRequests(QueryFriendRequest request) {
        Page<FriendRequest> pendingRequests = friendRequestService.getPendingRequests(request);
        return ResponseEntity.ok(new FriendRequestDTO<>(pendingRequests,"Pending friend requests retrieved successfully"));
    }
}
