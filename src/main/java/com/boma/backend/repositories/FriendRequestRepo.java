package com.boma.backend.repositories;

import com.boma.backend.enums.FriendRequestStatus;
import com.boma.backend.models.FriendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepo extends JpaRepository<FriendRequest, Long> {
    Page<FriendRequest> findByReceiverIdAndStatus(long receiverId, FriendRequestStatus status, Pageable pageable);
}
