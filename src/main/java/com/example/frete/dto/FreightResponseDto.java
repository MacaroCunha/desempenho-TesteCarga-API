package com.example.frete.dto;

import lombok.Data;

@Data
public class FreightResponseDto {
    private String message;
    private double finalPrice;
    private double totalWeightKg;
}


