package com.example.frete.service;

import com.example.frete.dto.response.FreightResponseDto;
import com.example.frete.dto.request.FreightRequestDto;
import com.example.frete.model.TableFreightModel;
import com.example.frete.repository.TableFreightRepository;
import org.hibernate.boot.model.internal.CreateKeySecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

            String finalPrice = calcularFaixaPeso(totalWeightKg);

            TableFreightModel finalPriceCalculated = tableFreightRepository.findFinalPrice(request.getAddress().getPostalCode(), finalPrice);


            return new FreightResponseDto("Processed successfully", finalPriceCalculated.getTariff(), totalWeightKg);
        } catch (IllegalArgumentException e) {
            String[] messages = {"Error: " + e.getMessage()};
            return new FreightResponseDto("Error when process the request.", 0.0, 0.0);
        }
    }
    public static String calcularFaixaPeso(Double pesoTotal) {
        if (pesoTotal <= 10) {
            return "0 a 10kg";
        } else if (pesoTotal <= 100) {
            return "10 a 100kg";
        } else if (pesoTotal <= 1000) {
            return "100kg a 1ton";
        } else {
            return "1ton a 10ton";
        }
    }
}
