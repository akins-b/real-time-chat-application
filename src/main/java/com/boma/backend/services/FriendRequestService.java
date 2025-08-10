package com.boma.backend.services;

import com.boma.backend.dto.request.QueryFriendRequest;
import com.boma.backend.models.FriendRequest;
import org.springframework.data.domain.Page;

public interface FriendRequestService {
    void sendFriendRequest(long senderId, long receiverId);
    void acceptFriendRequest(long requestId);
    void rejectFriendRequest(long requestId);
    Page<FriendRequest> getPendingRequests(QueryFriendRequest request);
}
