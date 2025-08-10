package com.boma.backend.dto.request;

import lombok.Data;

@Data
public class QueryFriendRequest {
    long userId;
    int page = 1;
    int size = 10;
}
