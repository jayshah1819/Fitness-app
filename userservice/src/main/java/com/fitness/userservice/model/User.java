package com.fitness.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data //for the getters and setters and required args constructor and
// toString and equalsAndHashcode


public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(unique=true, nullable=false)
    private String email;


    @Column(nullable=false)
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role=UserRole.USER;// WE WILL SEE THIS ERROR BECAUSE WE HAVE TO CREATE ENUM FOR THIS FORR
    //WE HAVE TO GO TO HOVER OVER IT AND IN MORE ACTIONS IT SHOULD BE ENUM ADD AND HAVE
    //ADDED IT IN PACKAGE CALL FITNESS.MODEL SOMETHING LIKE THIS BUT FOCUS SHOULD BE ON THE MODEL.
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
