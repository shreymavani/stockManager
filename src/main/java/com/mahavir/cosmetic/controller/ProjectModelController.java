package com.mahavir.cosmetic.controller;


import com.mahavir.cosmetic.model.ProjectModel;
import com.mahavir.cosmetic.service.ProjectModelService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projectModel")
public class ProjectModelController {

    @Autowired
    ProjectModelService projectModelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectModel createTask(@RequestBody ProjectModel projectModel){
        return projectModelService.addOrUpdateModel(projectModel);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProjectModel update(@RequestBody ProjectModel projectModel){
        return projectModelService.addOrUpdateModel(projectModel);
    }


    @PostMapping("/totalRequiredStockForProductionPlans")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, Long> productionPlanConsumptionReturnTotalQuantity(@RequestBody List<String> modelIds) {
        return projectModelService.totalQuantityRequired(modelIds);
    }

    @PostMapping("/availableStockInWareHouseThatIsRequiredForProductionPlan")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, Long> productionPlanConsumptionReturnCurrentQuantityInWareHouse(@RequestBody List<String> modelIds) {
        return projectModelService.currentQuantityInStock(modelIds);
    }

    @PostMapping("/possibleNumberTillProductionCanDone")
    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Map<String, String> productionPlanConsumptionPossible(@RequestBody List<String> modelIds) {
    public String productionPlanConsumptionPossible(@RequestBody List<String> modelIds) {
        return projectModelService.productionPlanConsumptionPossible(modelIds);
    }


    @GetMapping("/listAllModels")
    public List<ProjectModel> getAllProjectModels() {
        return projectModelService.listAllProjectModels();
    }

    @DeleteMapping("/{modelId}")
    public String deleteModel(@PathVariable String modelId){
        return projectModelService.deleteModel(modelId);
    }

    @DeleteMapping
    public String deleteAllInwards(){
        return projectModelService.deleteAllModel();
    }
}
