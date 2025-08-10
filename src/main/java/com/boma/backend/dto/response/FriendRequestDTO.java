package com.boma.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendRequestDTO<T> {
    private T data;
    private String message;
}
