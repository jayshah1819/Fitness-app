package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fitness.aiservice.model.Activity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ClaudeService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${claude.api.key}")
    private String apiKey;

    @Value("${claude.api.url}")
    private String apiUrl;

    @Value("${claude.api.model}")
    private String model;

    public ClaudeService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.anthropic.com/v1")
                .defaultHeader("x-api-key", apiKey)
                .defaultHeader("anthropic-version", "2023-06-01")
                .build();
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public String getAnswer(String prompt) {
        try {
            // Create the messages array
            ArrayNode messagesArray = objectMapper.createArrayNode();
            ObjectNode messageObject = objectMapper.createObjectNode()
                    .put("role", "user")
                    .put("content", prompt);
            messagesArray.add(messageObject);

            // Create the main request body
            ObjectNode requestBody = objectMapper.createObjectNode()
                    .put("model", "claude-3-opus-20240229")
                    .put("max_tokens", 1000)
                    .put("temperature", 0.7)
                    .put("system", "You are an AI fitness expert assistant. Provide detailed analysis and recommendations in JSON format.");
            requestBody.set("messages", messagesArray);

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

    public String getRecommendation(Activity activity) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", apiKey);
            headers.set("anthropic-version", "2023-06-01");

            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", model);
            requestBody.put("max_tokens", 1000);

            ObjectNode message = objectMapper.createObjectNode();
            message.put("role", "user");
            message.put("content", buildPrompt(activity));
            requestBody.putArray("messages").add(message);

            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            String response = restTemplate.postForObject(apiUrl, request, String.class);

            ObjectNode responseJson = (ObjectNode) objectMapper.readTree(response);
            return responseJson.path("content").path(0).path("text").asText();
        } catch (Exception e) {
            log.error("Error getting recommendation from Claude: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get recommendation from Claude", e);
        }
    }

    private String buildPrompt(Activity activity) {
        return String.format(
            "Based on the following fitness activity data, provide personalized recommendations:\n" +
            "Activity Type: %s\n" +
            "Duration: %.2f minutes\n" +
            "Distance: %.2f km\n" +
            "Calories Burned: %.2f\n" +
            "Heart Rate: %.2f bpm\n" +
            "Notes: %s\n\n" +
            "Please provide specific recommendations for improvement and next steps.",
            activity.getType(),
            activity.getDuration(),
            activity.getDistance(),
            activity.getCaloriesBurned(),
            activity.getHeartRate(),
            activity.getNotes()
        );
    }
} 