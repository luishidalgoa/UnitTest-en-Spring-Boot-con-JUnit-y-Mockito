package com.tutorialesvip.tutorialunittest.controller;

import com.tutorialesvip.tutorialunittest.models.Country;
import com.tutorialesvip.tutorialunittest.models.CountryResponse;
import com.tutorialesvip.tutorialunittest.repositories.CountryRepository;
import com.tutorialesvip.tutorialunittest.util.DiferenciaEntreFechas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Period;
import java.util.Optional;

@RestController()
public class IndependencyController {
    private final CountryRepository countryRepository;

    @Autowired
    public IndependencyController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping(path = "/country/{countryId}")
    public ResponseEntity<CountryResponse> getCountryDetails(@PathVariable("countryId") String countryId) {

        Optional<Country> country = Optional.ofNullable(countryRepository.findCountryByIsoCode(countryId.toUpperCase()));

        if (!country.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        CountryResponse countryResponse = new CountryResponse(country.get());
        return ResponseEntity.ok(countryResponse);
    }
}