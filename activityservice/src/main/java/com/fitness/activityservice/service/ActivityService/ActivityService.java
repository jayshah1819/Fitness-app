package com.fitness.activityservice.service.ActivityService;

import com.fitness.activityservice.ActivityRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

@Slf4j

public class ActivityService {
    private final ActivityRepository activityRepository;
    private final RabbitTemplate rabbitTemplate;

//Don't forget we have to add this inside of the function
    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final UserValidationService userValidationService;
    public ActivityResponse trackActivity(ActivityRequest request) {

        Activity activity= Activity.builder()
                .usersId(request.getUsersId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        //publish to mq for ai processing
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);

        } catch(Exception e){
            log.error("failed to publish activity using Rabbitmq", e);

        }


        return mapToResponse(savedActivity);


    }
    private ActivityResponse mapToResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUsersId(activity.getUsersId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;

    }


    public List<ActivityResponse> getActivities(String userId) {
        List<Activity> activities=activityRepository.findByUsersId(userId);
        return activities.stream().map(this::mapToResponse).collect(Collectors.toList());

    }

    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId).map(this::mapToResponse).orElseThrow(()->new RuntimeException("Activity not found"));
    }
}
