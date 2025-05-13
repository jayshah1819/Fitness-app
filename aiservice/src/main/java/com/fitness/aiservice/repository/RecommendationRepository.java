package com.fitness.aiservice.repository;

import com.fitness.aiservice.model.Recommendation;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
//it should be interface remember that.
@Repository
@EnableMongoAuditing
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
}
