package com.boma.backend.models;

import com.boma.backend.enums.FriendRequestStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Users receiver;

    private FriendRequestStatus status;

}
