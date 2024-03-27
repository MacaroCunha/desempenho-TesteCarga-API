package com.example.frete.service;

import com.example.frete.dto.FreightResponseDto;
import com.example.frete.dto.ItemDto;
import com.example.frete.dto.request.FreightRequestDto;
import com.example.frete.model.TableFreightModel;
import com.example.frete.repository.TableFreightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreightServiceImpl {

    private final TableFreightRepository tableFreightRepository;

    @Autowired
    public FreightServiceImpl(TableFreightRepository tableFreightRepository) {
        this.tableFreightRepository = tableFreightRepository;
    }

    public FreightResponseDto calculateFreight(FreightRequestDto request) {
        double totalPrice = 0.0;
        double totalWeightKg = 0.0;

        if (!isValidCnpj(request.getCompany())) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }

        if (!isValidCep(request.getAddress().getPostalCode())) {
            throw new IllegalArgumentException("Invalid CEP");
        }

        for (ItemDto item : request.getItems()) {
            double itemWeight = item.getProduct_weight();
            String weightUnit = item.getWeight_unit();

            if (itemWeight > 10000) {
                throw new IllegalArgumentException("Weight of an item exceeds maximum limit");
            }

            switch (weightUnit.toLowerCase()) {
                case "kg":
                    totalWeightKg += itemWeight;
                    break;
                case "gr":
                    totalWeightKg += itemWeight / 1000.0;
                    break;
                case "ton":
                    totalWeightKg += itemWeight * 1000.0;
                    break;
            }
            totalPrice += item.getUnit_price() * item.getQuantity();
        }

        double freightPrice = calculateFreightPrice(totalWeightKg, request.getAddress().getPostalCode());

        FreightResponseDto responseDto = new FreightResponseDto();
        responseDto.setMessage("Processed successfully");
        responseDto.setFinalPrice(freightPrice);
        responseDto.setTotalWeightKg(totalWeightKg);

        return responseDto;
    }

    private boolean isValidCnpj(String cnpj) {
        return true;
    }

    private boolean isValidCep(String cep) {
        return true;
    }

    private double calculateFreightPrice(double totalWeightKg, String cep) {
        TableFreightModel tableFreight = tableFreightRepository
                .findByCepStartLessThanEqualAndCepEndGreaterThanEqual(cep, cep);
        if (tableFreight != null) {
            return tableFreight.getTariff();
        }
        return 0.0;
    }
}










