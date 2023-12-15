package com.mahavir.cosmetic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inwards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inward {
    @Id
    private String itemEntry;
    private String itemName;
    private String date;
    private String emailAddress;
    private String sign;
    private String category;
    private String Type;
    private long box;
    private long unitPerBox;
    private long looseUnits;
    private long totalUnits;
    private String storageRoom;
    private String vehicle;
    private String unloadingVehicle;
}
