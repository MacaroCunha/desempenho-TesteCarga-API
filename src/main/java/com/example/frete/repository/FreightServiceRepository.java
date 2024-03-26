package com.example.frete.repository;

import com.example.frete.dto.request.FreightRequestDto;
import com.example.frete.model.FreightResponse;

public interface FreightServiceRepository {
    FreightResponse calculateFreight(FreightRequestDto request);
}

