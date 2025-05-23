package com.fitness.aiservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.service.ActivityAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final ObjectMapper objectMapper;
    private final MessageConverter messageConverter;

    @RabbitListener(queues = "activity.queue", containerFactory = "rabbitListenerContainerFactory")
    public void handleActivityMessage(Message message) {
        try {
            log.info("Received activity message: {}", new String(message.getBody()));
            Activity activity = (Activity) messageConverter.fromMessage(message);
            log.info("Parsed activity: {}", activity);
            activityAIService.processActivity(activity);
            log.info("Successfully processed activity for user: {}", activity.getUserId());
        } catch (Exception e) {
            log.error("Error processing activity message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process activity message", e);
        }
    }
} 