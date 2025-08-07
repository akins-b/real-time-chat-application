package com.boma.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BrevoEmailBodyRequest {
    private Sender sender;
    private String subject;
    private String htmlContent;
    private Recipient recipient;


    @Data
    @AllArgsConstructor
    public static class Sender {
        String email;
        String name;
    }

    @Data
    @AllArgsConstructor
    public static class Recipient{
        String email;
        String name;
    }

}