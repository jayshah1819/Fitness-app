package com.fitness.aiservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {
    @Id
    private String id;
    private String usersId;
    private String type;
    private Integer duration;
    private Integer caloriesBurned;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    private Map<String, Object> additionalMetrics;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public String getusersId() {
        return usersId;
    }
}
