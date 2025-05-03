package com.fitness.activityservice.service.ActivityService;

import com.fitness.activityservice.ActivityRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isValidUser= userValidationService.validateUser(request.getUsersId());
        if(!isValidUser){
            throw new RuntimeException("Invalid user"+request.getUsersId());
        }

        Activity activity= Activity.builder()
                .usersId(request.getUsersId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
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
