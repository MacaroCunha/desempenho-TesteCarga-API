package com.example.frete.dto;

public class ItemDto {
    private String nome;
    private int quantidade;
    private double preco_unitario;
    private double peso_produto;
    private String medida_peso;

    public String getWeightUnit() {
        return null;
    }

    public double getWeight() {
        return 0;
    }
}
