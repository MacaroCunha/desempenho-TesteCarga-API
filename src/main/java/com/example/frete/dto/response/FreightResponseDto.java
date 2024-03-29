package com.example.frete.dto.response;

import lombok.Data;

@Data
public class FreightResponseDto {
    private String messages;
    private double finalPrice;
    private double totalWeightKg;

    public FreightResponseDto(String messages, double finalPrice, double totalWeightKg) {
        this.messages = messages;
        this.finalPrice = finalPrice;
        this.totalWeightKg = totalWeightKg;
    }
}



