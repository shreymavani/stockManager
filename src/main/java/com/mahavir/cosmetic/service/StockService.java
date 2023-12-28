package com.mahavir.cosmetic.service;

import com.mahavir.cosmetic.model.Stock;
import com.mahavir.cosmetic.repository.StockRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private static final Logger logger = LogManager.getLogger(StockService.class);

    @Autowired
    StockRepository stockRepository;

    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public String updateStocks(Map<String, Long> itemNameAndQuantityToReduce) {
        for (Map.Entry<String, Long> entry : itemNameAndQuantityToReduce.entrySet()) {
            Optional<Stock> stock = getStockBasedOnName(entry.getKey());
            if (stock.isPresent()) {
                long currentStock = stock.get().getTotalStock();
                Optional<Stock> newStock = updateStockBasedOnName(entry.getKey(), currentStock - entry.getValue());
                if (newStock.isEmpty()) {
                    logger.warn("Error while updating the stock for itemName :: " + entry.getValue());
                }
            }
        }
        logger.info("All stocks for modelIds are reduced with respected quantities :: " + itemNameAndQuantityToReduce);
        return "All stocks for modelIds are updated Successfully :: ";
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockBasedOnName(String itemName) {return stockRepository.findById(itemName);}

    public List<Stock> getStockBasedOnCategory(String category) {return stockRepository.findByCategory(category);}

    public List<Stock> getStockBasedOnType(String type) {return stockRepository.findByType(type);}

    public List<Stock> getStockBasedOnCategoryAndType(String category, String type) {return stockRepository.findByCategoryAndType(category, type);}

    public String deleteAll() {
        stockRepository.deleteAll();
        return "ALL Stocks are deleted successfully";
    }

    public Optional<Stock> updateStockBasedOnName(String itemName, Long quantity) {
        Optional<Stock> stock = getStockBasedOnName(itemName);
        if (stock.isPresent()) {
            stock.get().setTotalStock(quantity);
            return Optional.ofNullable(addStock(stock.get()));
        }
        return Optional.empty();
    }
}
