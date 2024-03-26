package com.example.frete.controller;

import com.example.frete.dto.AddressDto;
import com.example.frete.dto.YourRequestClass;
import com.example.frete.model.FreightResponse;
import com.example.frete.repository.FreightServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
public class FreightController {

    private final FreightServiceRepository freightRepository;

    @Autowired
    public FreightController(FreightServiceRepository freightRepository) {
        this.freightRepository = freightRepository;
    }

    @GetMapping("/example-get")
    public ResponseEntity<String> exampleGetMethod() {
        return new ResponseEntity<>("Example GET method", HttpStatus.OK);
    }

    @PutMapping("/update-address")
    public ResponseEntity<String> updateAddress(@RequestBody AddressDto addressDto) {
        // Update address logic here
        return new ResponseEntity<>("Address updated successfully", HttpStatus.OK);
    }
}




