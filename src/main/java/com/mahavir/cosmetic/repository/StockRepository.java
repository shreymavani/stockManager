package com.mahavir.cosmetic.repository;

import com.mahavir.cosmetic.model.Stock;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<Stock, String> {

    List<Stock> findByCategory(String category);

    List<Stock> findByType(String category);

    List<Stock> findByCategoryAndType(String category, String type);
}
