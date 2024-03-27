package com.example.frete.controller;

import com.example.frete.dto.FreightResponseDto;
import com.example.frete.dto.request.FreightRequestDto;
import com.example.frete.service.FreightServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipping")
public class FreightController {

    private final FreightServiceImpl freightService;

    @Autowired
    public FreightController(FreightServiceImpl freightService) {
        this.freightService = freightService;
    }

    @PostMapping("/calculate-freight")
    public ResponseEntity<FreightResponseDto> calculateFreight(@RequestBody FreightRequestDto requestDto) {
        FreightResponseDto responseDto = freightService.calculateFreight(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}






