package com.example.frete.dto;

import lombok.Data;

@Data
public class ItemDto {
    private String name;
    private int quantity;
    private double unit_price;
    private double product_weight;
    private String weight_unit;
}




