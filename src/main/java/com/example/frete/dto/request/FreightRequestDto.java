package com.example.frete.dto.request;

import com.example.frete.dto.AddressDto;
import com.example.frete.dto.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class FreightRequestDto {
    private String company;
    private String cnpj;
    private AddressDto address;
    private List<ProductDto> items;
}



