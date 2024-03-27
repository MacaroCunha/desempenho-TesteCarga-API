package com.example.frete.dto.request;

import com.example.frete.dto.AddressDto;
import com.example.frete.dto.ItemDto;
import lombok.Data;

import java.util.List;

@Data
public class FreightRequestDto {
    private String company;
    private String cnpj;
    private AddressDto address;
    private List<ItemDto> items;
}



