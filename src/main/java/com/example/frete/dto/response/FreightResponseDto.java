package com.example.frete.dto.response;

import lombok.Data;

@Data
public class FreightResponseDto {
    private String[] Message;
    private double finalPrice;
    private double totalWeightKg;

    public FreightResponseDto(String[] Message, double finalPrice, double totalWeightKg) {
        this.Message = Message;
        this.finalPrice = finalPrice;
        this.totalWeightKg = totalWeightKg;
    }
}



