package com.fitness.aiservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.service.ActivityAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "activity.queue")
    public void handleActivityMessage(String message) {
        try {
            log.info("Received activity message: {}", message);
            Activity activity = objectMapper.readValue(message, Activity.class);
            activityAIService.processActivity(activity);
        } catch (Exception e) {
            log.error("Error processing activity message: {}", e.getMessage(), e);
        }
    }
} 