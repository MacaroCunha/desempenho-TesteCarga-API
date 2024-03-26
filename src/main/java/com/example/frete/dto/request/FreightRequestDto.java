package com.example.frete.dto.request;

import com.example.frete.dto.AddressDto;
import com.example.frete.dto.ItemDto;
import jdk.jfr.DataAmount;

import java.util.List;

@DataAmount
public class FreightRequestDto {
    private String company;
    private String cnpj;
    private AddressDto address;
    private List<ItemDto> items;
    public ItemDto[] getItems() {
        return items.toArray(new ItemDto[0]);
    }
}


