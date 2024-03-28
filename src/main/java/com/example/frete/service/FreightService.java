package com.example.frete.service;

import com.example.frete.dto.response.FreightResponseDto;
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
            double totalPrice = request.getItems().stream()
                    .mapToDouble(item -> item.getUnit_price() * item.getQuantity())
                    .sum();

            double totalWeightKg = request.getItems().stream()
                    .mapToDouble(item -> {
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
                    })
                    .sum();

            if (totalWeightKg > 10000) {
                throw new IllegalArgumentException("Total weight exceeds 10 tons");
            }

            String cep = request.getAddress().getPostalCode();
            List<TableFreightModel> tableFreightList = tableFreightRepository
                    .findAllByCepStartLessThanEqualAndCepEndGreaterThanEqual(cep, cep);

            double freightPrice = 0.0;
            for (TableFreightModel tableFreight : tableFreightList) {
                int cepStart = Integer.parseInt(tableFreight.getCepStart().replace("-", ""));
                int cepEnd = Integer.parseInt(tableFreight.getCepEnd().replace("-", ""));
                int cepValue = Integer.parseInt(cep.replace("-", ""));
                if (tableFreight.getTariff() != null && cepValue >= cepStart && cepValue <= cepEnd) {
                    freightPrice = tableFreight.getTariff() * totalWeightKg;
                    break;
                }
            }

            return new FreightResponseDto(new String[]{"Processed successfully"}, freightPrice + totalPrice, totalWeightKg);
        } catch (IllegalArgumentException e) {
            return new FreightResponseDto(new String[]{"Error: " + e.getMessage()}, 0.0, 0.0);
        }
    }
}


