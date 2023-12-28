package com.mahavir.cosmetic.service;

import com.mahavir.cosmetic.model.Stock;
import java.util.HashMap;
import java.util.List;
import com.mahavir.cosmetic.model.ProjectModel;
import com.mahavir.cosmetic.repository.ProjectModelRepository;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProjectModelService {

    private static final Logger logger = LogManager.getLogger(ProjectModelService.class);

    @Autowired
    ProjectModelRepository projectModelRepository;

    @Autowired
    StockService stockService;

    public ProjectModel addOrUpdateModel(ProjectModel model) {
        logger.info("Project model added or Updated successfully :: " + model);
        return projectModelRepository.save(model);
    }

    public Optional<ProjectModel> getModel(String modelId) {
        return projectModelRepository.findById(modelId);
    }

    public List<ProjectModel> listAllProjectModels() {
        return projectModelRepository.findAll();
    }

    public String productionPlanConsumptionPossible(@RequestBody List<String> modelIds) {
        Map<String, Long> totalQuantityRequired = new HashMap<>();
        Map<String, Long> totalQuantityAvailable = currentQuantityInStock(modelIds);
        String firstStockInsufficiency = "";
        //        int inSufficient = 0;
        //        String listOfInSufficientItem;

        for (String id : modelIds) {
            Optional<ProjectModel> projectModel = getModel(id);

            if (projectModel.isPresent()) {
                Map<String, Long> quantities = projectModel.get().getQuantity();
                for (Map.Entry<String, Long> entry : quantities.entrySet()) {
                    if (totalQuantityRequired.containsKey(entry.getKey())) {
                        totalQuantityRequired.put(entry.getKey(), totalQuantityRequired.get(entry.getKey()) + entry.getValue());
                    } else {
                        totalQuantityRequired.put(entry.getKey(), entry.getValue());
                    }

                    if (totalQuantityRequired.get(entry.getKey()) > totalQuantityAvailable.get(entry.getKey())) {
                        logger.info("This Production model is not possible for production because of insufficient quantity of " + entry.getKey() + " Current available quantity: " + totalQuantityAvailable.get(
                                entry.getKey()) + "Total Required quantity: " + totalQuantityRequired.get((entry.getKey())));
                        return firstStockInsufficiency;
                    }
                    //                        inSufficient = 1;
                    //                        listOfInSufficientItem += entry.getKey() + "," + totalQuantityRequired.get(entry.getKey());
                    //                    }
                }
            }
            firstStockInsufficiency = id;
        }

        logger.info("Project models planning for successfully calculated :: " + firstStockInsufficiency);

        return firstStockInsufficiency;
    }

    public String deleteModel(String projectId) {
        projectModelRepository.deleteById(projectId);
        logger.info("Model deleted successfully with projectId :: " + projectId);
        return "Model is deleted successfully with projectId" + projectId;
    }

    public String deleteAllModel() {
        projectModelRepository.deleteAll();
        logger.info("All models are deleted successfully");
        return "All models are deleted successfully";
    }

    public Map<String, Long> totalQuantityRequired(List<String> modelIds) {
        Map<String, Long> totalQuantity = new HashMap<>();

        for (String id : modelIds) {

            Optional<ProjectModel> projectModel = getModel(id);
            if (projectModel.isPresent()) {
                Map<String, Long> quantities = projectModel.get().getQuantity();

                for (Map.Entry<String, Long> entry : quantities.entrySet()) {
                    if (totalQuantity.containsKey(entry.getKey())) {
                        totalQuantity.put(entry.getKey(), totalQuantity.get(entry.getKey()) + entry.getValue());
                    } else {
                        totalQuantity.put(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                logger.info("Project model is not found for modelId :: " + id + "So quantity is not added for this modelId");
            }
        }

        logger.info("Total Quantity Required :: " + totalQuantity + "for modelIds :: " + modelIds);
        return totalQuantity;
    }

    public Map<String, Long> currentQuantityInStock(List<String> modelIds) {
        Map<String, Long> currentQuantity = new HashMap<>();
        Map<String, Long> totalQuantity = totalQuantityRequired(modelIds);

        for (Map.Entry<String, Long> entry : totalQuantity.entrySet()) {
            Optional<Stock> currentStock = stockService.getStockBasedOnName(entry.getKey());

            if (currentStock.isPresent()) {currentQuantity.put(entry.getKey(), currentStock.get().getTotalStock());} else {
                currentQuantity.put(entry.getKey(), 0L);
            }
        }

        logger.info("Total Quantity in stocks :: " + currentQuantity + "for modelIds :: " + modelIds);
        return currentQuantity;
    }
}
