package com.fitness.activityservice.service.ActivityService;

import com.fitness.activityservice.ActivitRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ActivityService {
    private ActivitRepository activitRepository;
    public ActivityResponse trackActivity(ActivityRequest request) {

    }
}
