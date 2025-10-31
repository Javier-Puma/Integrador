package com.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleEntryRequest {
    private String licensePlate;
    private String color;
    private String type; // MOTORCYCLE, CAR, LARGE
    private int spaceCount = 1;
    private Double discountAmount; //optional
}