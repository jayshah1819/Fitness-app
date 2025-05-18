package com.fitness.aiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
public class GeminiService {
    private final WebClient webClient;

    @Value("${GEMINI_API_URL}")
    private String geminiApiUrl;

    @Value("${GEMINI_API_KEY}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(geminiApiUrl).build();
    }

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text", question)
                                }
                        )
                }
        );

        try {
            return webClient.post()
                    .uri("/some-endpoint") // Use a specific endpoint path here
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + geminiApiKey) // API Key in header for security
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            // Log the error or handle as needed
            return "Error: " + ex.getMessage();
        } catch (Exception ex) {
            // Handle other types of exceptions
            return "An unexpected error occurred: " + ex.getMessage();
        }
    }
}
