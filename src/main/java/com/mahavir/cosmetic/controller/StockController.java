package com.mahavir.cosmetic.controller;

import com.mahavir.cosmetic.model.Stock;
import com.mahavir.cosmetic.service.InwardService;
import com.mahavir.cosmetic.service.ProjectModelService;
import com.mahavir.cosmetic.service.StockService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    private static final Logger logger = LogManager.getLogger(InwardService.class);

    @Autowired
    private StockService stockService;

    @Autowired
    ProjectModelService projectModelService;

    @PutMapping("/updateStockUsedForGivenProductionPlan")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String updateStockUsedForGivenProductionPlan(@RequestBody List<String> modelIds) {
        Map<String, Long> requiredStockForProduction = projectModelService.totalQuantityRequired(modelIds);
        logger.info("Updating Stocks for modelIds are under process ::" + modelIds);
        return stockService.updateStocks(requiredStockForProduction);
    }

    @PutMapping("/{itemName}/reduceStockBy/{quantityToReduce}")
    public Optional<Stock> updateStockBasedOnName(@PathVariable String itemName, @PathVariable Long quantityToReduce){
        logger.info("Updating Stock for itemName is under process ::" + itemName);
        return stockService.updateStockBasedOnName(itemName, quantityToReduce);
    }

    @GetMapping("/{itemName}")
    public Optional<Stock> getStockBasedOnName(@PathVariable String itemName) {return stockService.getStockBasedOnName(itemName);}

    @GetMapping
    public List<Stock> getStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/category/{category}")
    public List<Stock> getStocksBasedOnCategory(@PathVariable String category) {return stockService.getStockBasedOnCategory(category);}

    @GetMapping("/type/{type}")
    public List<Stock> getStocksBasedOnType(@PathVariable String type) {
        return stockService.getStockBasedOnType(type);
    }

    @GetMapping("/{category}/{type}")
    public List<Stock> getStocksBasedOnType(@PathVariable String category,@PathVariable String type) {
        return stockService.getStockBasedOnCategoryAndType(category, type);
    }

    @DeleteMapping
    public String deleteAllStocks(){
        return stockService.deleteAll();
    }
}
