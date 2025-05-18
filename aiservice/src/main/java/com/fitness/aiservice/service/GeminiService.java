package com.fitness.aiservice.service;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class GeminiService {
    private final WebClient webClient;


    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();

    }

    public String getAnswer(String question) {
        Map<String, Object> request = Map.of(

                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text", question)

                                }
                        )
                }
        );

        return request.toString();
    }
}
