package com.example.frete.service;

import com.example.frete.dto.ItemDto;
import com.example.frete.dto.request.FreightRequestDto;
import com.example.frete.model.FreightResponse;
import com.example.frete.repository.FreightServiceRepository;
import org.springframework.stereotype.Service;

@Service
public class FreightServiceImpl implements FreightServiceRepository {

    @Override
    public FreightResponse calculateFreight(FreightRequestDto request) {
        double precoFinalFrete = 0.0;
        double pesoTotalKg = 0.0;

        for (ItemDto item : request.getItems()) {
            if ("kg".equalsIgnoreCase(item.getWeightUnit())) {
                pesoTotalKg += item.getWeight();
            } else if ("gr".equalsIgnoreCase(item.getWeightUnit())) {
                pesoTotalKg += item.getWeight() / 1000.0; // Converter gramas para quilogramas
            } else if ("ton".equalsIgnoreCase(item.getWeightUnit())) {
                pesoTotalKg += item.getWeight() * 1000.0; // Converter toneladas para quilogramas
            }
        }

        FreightResponse response = new FreightResponse();
        response.setMensagem("processado com sucesso");
        response.setPrecoFinalFrete(precoFinalFrete);
        response.setPesoTotalKg(pesoTotalKg);

        return response;
    }
}



