package com.example.frete.service;

import com.example.frete.dto.response.FreightResponseDto;
import com.example.frete.dto.ProductDto;
import com.example.frete.dto.request.FreightRequestDto;
import com.example.frete.model.TableFreightModel;
import com.example.frete.repository.TableFreightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreightService {

    private final TableFreightRepository tableFreightRepository;

    @Autowired
    public FreightService(TableFreightRepository tableFreightRepository) {
        this.tableFreightRepository = tableFreightRepository;
    }

    public FreightResponseDto calculateFreight(FreightRequestDto request) {
        try {
            validateRequest(request);

            double totalPrice = calculateTotalPrice(request);
            double totalWeightKg = calculateTotalWeight(request);

            if (totalWeightKg > 10000) {
                throw new IllegalArgumentException("Total weight exceeds 10 tons");
            }

            double freightPrice = calculateFreightPrice(totalWeightKg, request.getAddress().getPostalCode());

            markRequestAsProcessed(request.getAddress().getPostalCode());

            return new FreightResponseDto(new String[]{"Processed successfully"}, freightPrice + totalPrice, totalWeightKg);
        } catch (IllegalArgumentException e) {
            return new FreightResponseDto(new String[]{"Error: " + e.getMessage()}, 0.0, 0.0);
        }
    }

    private void validateRequest(FreightRequestDto request) {
        if (!isValidCnpj(request.getCompany())) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }

        if (!isValidCep(request.getAddress().getPostalCode())) {
            throw new IllegalArgumentException("Invalid CEP");
        }
    }

    private boolean isValidCnpj(String cnpj) {
        return true;
    }

    private boolean isValidCep(String cep) {
        return true;
    }

    private double calculateTotalPrice(FreightRequestDto request) {
        return request.getItems().stream()
                .mapToDouble(item -> item.getUnit_price() * item.getQuantity())
                .sum();
    }

    private double calculateTotalWeight(FreightRequestDto request) {
        return request.getItems().stream()
                .mapToDouble(this::calculateItemWeightInKg)
                .sum();
    }

    private double calculateItemWeightInKg(ProductDto item) {
        double itemWeight = item.getProduct_weight();
        switch (item.getWeight_unit().toLowerCase()) {
            case "kg":
                return itemWeight * item.getQuantity();
            case "gr":
                return (itemWeight * item.getQuantity()) / 1000.0;
            case "ton":
                return (itemWeight * item.getQuantity()) * 1000.0;
            default:
                throw new IllegalArgumentException("Invalid weight unit: " + item.getWeight_unit());
        }
    }

    private double calculateFreightPrice(double totalWeightKg, String cep) {
        List<TableFreightModel> tableFreightList = tableFreightRepository
                .findAllByCepStartLessThanEqualAndCepEndGreaterThanEqual(cep, cep);
        for (TableFreightModel tableFreight : tableFreightList) {
            if (tableFreight.getTariff() != null && isCepInRange(cep, tableFreight)) {
                return tableFreight.getTariff() * totalWeightKg;
            }
        }
        return 0.0;
    }

    private boolean isCepInRange(String cep, TableFreightModel tableFreight) {
        int cepStart = Integer.parseInt(tableFreight.getCepStart().replace("-", ""));
        int cepEnd = Integer.parseInt(tableFreight.getCepEnd().replace("-", ""));
        int cepValue = Integer.parseInt(cep.replace("-", ""));
        return cepValue >= cepStart && cepValue <= cepEnd;
    }

    private void markRequestAsProcessed(String cep) {
        List<TableFreightModel> tableFreightList = tableFreightRepository
                .findAllByCepStartLessThanEqualAndCepEndGreaterThanEqual(cep, cep);
        for (TableFreightModel tableFreight : tableFreightList) {
            saveProcessedRequest(tableFreight);
        }
    }

    private void saveProcessedRequest(TableFreightModel tableFreight) {
        tableFreightRepository.save(tableFreight);
    }
}

