package com.mahavir.cosmetic.service;

import com.mahavir.cosmetic.model.Stock;
import com.mahavir.cosmetic.repository.StockRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    public Stock addStock(Stock stock){
        return stockRepository.save(stock);
    }

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockBasedOnName(String itemName){return stockRepository.findById(itemName);}

    public List<Stock> getStockBasedOnCategory(String category){ return stockRepository.findByCategory(category);}

    public List<Stock> getStockBasedOnType(String type){ return stockRepository.findByType(type);}

    public List<Stock> getStockBasedOnCategoryAndType(String category, String type){ return stockRepository.findByCategoryAndType(category, type);}

    public String deleteAll(){
        stockRepository.deleteAll();
        return "ALL Stocks are deleted successfully";
    }

}
