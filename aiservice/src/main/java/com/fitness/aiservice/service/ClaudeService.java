package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ClaudeService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${claude.api.key}")
    private String apiKey;

    public ClaudeService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.anthropic.com/v1")
                .defaultHeader("x-api-key", apiKey)
                .defaultHeader("anthropic-version", "2023-06-01")
                .build();
        this.objectMapper = objectMapper;
    }

    public String getAnswer(String prompt) {
        try {
            ObjectNode requestBody = objectMapper.createObjectNode()
                    .put("model", "claude-3-opus-20240229")
                    .put("max_tokens", 1000)
                    .put("temperature", 0.7)
                    .put("system", "You are an AI fitness expert assistant. Provide detailed analysis and recommendations in JSON format.")
                    .put("messages", objectMapper.createArrayNode().add(
                            objectMapper.createObjectNode()
                                    .put("role", "user")
                                    .put("content", prompt)
                    ));

            String response = webClient.post()
                    .uri("/messages")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode responseNode = objectMapper.readTree(response);
            return responseNode.path("content").path(0).path("text").asText();

        } catch (Exception ex) {
            log.error("Error calling Claude API: ", ex);
            throw new RuntimeException("Failed to get response from Claude API: " + ex.getMessage());
        }
    }
} 