package com.mahavir.cosmetic.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "projectModel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectModel {
    @Id
    private String projectName;
    private Map<String, Long> quantity;
}
