package com.fitness.activityservice;

import com.fitness.activityservice.model.Activity;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableMongoAuditing

public interface ActivityRepository extends MongoRepository<Activity, String> {

    List<Activity> findByUsersId(String userId);
}
