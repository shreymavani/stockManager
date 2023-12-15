package com.mahavir.cosmetic.repository;

import com.mahavir.cosmetic.model.Inward;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InwardRepository extends MongoRepository<Inward, String> {

    List<Inward> findByDate(String date);
}