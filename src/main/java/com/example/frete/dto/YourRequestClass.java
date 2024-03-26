package com.example.frete.dto;

import com.example.frete.model.Address;
import com.example.frete.model.Item;
import java.util.List;

public class YourRequestClass {
    private String empresa;
    private String cnpj;
    private Address endereco;
    private List<Item> itens;
}
