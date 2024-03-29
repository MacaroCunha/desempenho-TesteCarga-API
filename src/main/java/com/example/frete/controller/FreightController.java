package com.example.frete.controller;

import com.example.frete.dto.response.FreightResponseDto;
import com.example.frete.dto.request.FreightRequestDto;
import com.example.frete.service.FreightService;
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

    private final FreightService freightService;

    @Autowired
    public FreightController(FreightService freightService) {
        this.freightService = freightService;
    }

    @PostMapping("/calculate-freight")
    public ResponseEntity<FreightResponseDto> calculateFreight(@RequestBody FreightRequestDto requestDto) {
        FreightResponseDto responseDto = freightService.calculateFreight(requestDto);
        if (responseDto.getMessages().startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
        return ResponseEntity.ok(responseDto);
    }
}





