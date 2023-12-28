package com.mahavir.cosmetic.service;

import com.mahavir.cosmetic.model.Inward;
import com.mahavir.cosmetic.model.Stock;
import com.mahavir.cosmetic.repository.InwardRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class InwardService {

    private final static String TIME_FORMATE = "dd-MM-yyyy HH:mm:ss";

    private static final Logger logger = LogManager.getLogger(InwardService.class);

    @Autowired
    InwardRepository inwardRepository;

    @Autowired
    StockService stockService;

    public Inward addInward(Inward inward) {
        inward.setItemEntry(UUID.randomUUID().toString().split("-")[0]);
        inward.setDate(getCurrentDateAndTime());
        long totalStock = inward.getBox() * inward.getUnitPerBox() + inward.getLooseUnits();
        inward.setTotalUnits(totalStock);
        Optional<Stock> stock = stockService.getStockBasedOnName(inward.getItemName());
        if (stock.isPresent()) {
            stockService.addStock(new Stock(
                    inward.getItemName(),
                    inward.getCategory(),
                    inward.getType(),
                    getCurrentDateAndTime(),
                    totalStock + stock.get().getTotalStock()));
        } else {stockService.addStock(new Stock(inward.getItemName(), inward.getCategory(), inward.getType(), getCurrentDateAndTime(), totalStock));}
        logger.info("Inward added successfully :: " + inward);
        return inwardRepository.save(inward);
    }

    public List<Inward> getInwardByDate(String date) {
        return inwardRepository.findByDate(date);
    }

    public List<Inward> findAllInwards() {
        return inwardRepository.findAll();
    }

    public String deleteTask(String itemId) {
        inwardRepository.deleteById(itemId);
        logger.info("Inward deleted successfully for inwardId:: " + itemId);
        return itemId + " inward deleted from dashboard ";
    }

    public String deleteAll() {
        inwardRepository.deleteAll();
        logger.info("All Inwards are deleted successfully for inwardId");
        return "ALL inwards are deleted successfully";
    }

    private String getCurrentDateAndTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId indiaTimeZone = ZoneId.of("Asia/Kolkata");
        ZonedDateTime indiaDateTime = ZonedDateTime.of(localDateTime, indiaTimeZone);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMATE);
        return indiaDateTime.format(formatter);
    }
}
