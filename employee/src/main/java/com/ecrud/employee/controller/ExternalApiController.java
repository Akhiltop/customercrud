package com.ecrud.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecrud.employee.service.ExternalApiService;

@RestController
@RequestMapping("/api/proxy")
@CrossOrigin(origins = "*")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    @Autowired
    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/customers")
    public String getExternalCustomers() {
        return externalApiService.fetchExternalCustomers();
    }
}
