package com.boma.backend.services.impl;

import com.boma.backend.dto.request.BrevoEmailBodyRequest;
import com.boma.backend.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final WebClient brevoWebClient;

    @Override
    public void sendEmail(String toEmail, String firstName, String lastName, String url){
        BrevoEmailBodyRequest request = new BrevoEmailBodyRequest();
        request.setSender(new BrevoEmailBodyRequest.Sender("akinsboms@gmail.com", "Boma"));
        request.setRecipient(new BrevoEmailBodyRequest.Recipient(toEmail, firstName + " " + lastName));
        request.setSubject("Account Verification");
        request.setHtmlContent("<p>Hi " + firstName + " " + lastName + ",<br>Click the link to verify your account:<br>" + url +"</p>");

        brevoWebClient.post()
                .uri("smptp/email")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error ->
                        System.err.println("Error sending email: " +error.getMessage()))
                .subscribe();

    }


}
