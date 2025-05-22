package com.fitness.aiservice.controller;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.service.ActivityAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final ActivityAIService activityAIService;

    @PostMapping("/analyze")
    public Recommendation testAnalyze(@RequestBody Activity activity) {
        return activityAIService.generateRecommendation(activity);
    }
} 