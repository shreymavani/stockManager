package com.mahavir.cosmetic.repository;

import com.mahavir.cosmetic.model.ProjectModel;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProjectModelRepository  extends MongoRepository<ProjectModel, String> {
}
