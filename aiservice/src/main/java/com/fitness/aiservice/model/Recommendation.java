package com.fitness.aiservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "recommendations")
public class Recommendation {
    @Id
    private String id;

    private String activityId;
    private String userId;
    private String activityType;

    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;

    // New rich structured fields
    private String aiPoweredRecoveryTip;
    private String smartSnackPairing;
    private String metabolicRepairMode;
    private String futureProjections;
    private List<String> funFacts;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
