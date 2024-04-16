package com.countries.info.controller;

import com.countries.info.exception.FailedRestApiRequestException;
import com.countries.info.service.FetchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final FetchService fetchService;

    public Controller(FetchService fetchService) {
        this.fetchService = fetchService;
    }

    @GetMapping("/country/{countryName}")
    public ResponseEntity getCountryInfo(@PathVariable String countryName) {
        try {
            String info = fetchService.fetchCountriesInformation(countryName);

            return new ResponseEntity(info, HttpStatus.OK);
        } catch (FailedRestApiRequestException e) {
            return new ResponseEntity("Try another country name, or try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
