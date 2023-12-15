package com.mahavir.cosmetic.controller.inwardController;

import com.mahavir.cosmetic.model.Stock;
import com.mahavir.cosmetic.service.StockService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/{itemName}")
    public Optional<Stock> getStockBasedOnName(@PathVariable String itemName) {
        return stockService.getStockBasedOnName(itemName);
    }

    @GetMapping
    public List<Stock> getStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/category/{category}")
    public List<Stock> getStocksBasedOnCategory(String category) {
        return stockService.getStockBasedOnCategory(category);
    }

    @GetMapping("/type/{type}")
    public List<Stock> getStocksBasedOnType(String type) {
        return stockService.getStockBasedOnType(type);
    }

    @GetMapping("/{category}/{type}")
    public List<Stock> getStocksBasedOnType(String category, String type) {
        return stockService.getStockBasedOnCategoryAndType(category, type);
    }

    @DeleteMapping
    public String deleteAllStocks(){
        return stockService.deleteAll();
    }
}
