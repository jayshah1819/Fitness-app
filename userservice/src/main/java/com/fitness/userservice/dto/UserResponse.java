package com.fitness.userservice.dto;
import lombok.Data;

import java.time.LocalDateTime;
//Data
@Data
public class UserResponse {
    private String id;
    private String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
