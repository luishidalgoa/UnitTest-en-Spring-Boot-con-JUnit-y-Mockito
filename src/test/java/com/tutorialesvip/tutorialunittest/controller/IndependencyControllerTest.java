package com.tutorialesvip.tutorialunittest.controller;

import com.tutorialesvip.tutorialunittest.models.Country;
import com.tutorialesvip.tutorialunittest.models.CountryResponse;
import com.tutorialesvip.tutorialunittest.repositories.CountryRepository;
import com.tutorialesvip.tutorialunittest.util.DiferenciaEntreFechas;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class) // Activa soporte de Mockito para inyectar @Mock y @InjectMocks con JUnit 5
class IndependencyControllerTest {

    @Mock
    CountryRepository countryRepositoryMock;

    //Injectamos el mock anteriormente creado
    @InjectMocks
    private IndependencyController independencyController;

    @BeforeEach
    void setUp() {
        {
            // Otra forma de hacer un mock de dependencias
            //CountryRepository countryRepositoryMock = Mockito.mock(CountryRepository.class);
            //independencyController = new IndependencyController(countryRepositoryMock);
        }
    }

    @Test
    void getCountryDetailsWithValidCountryCode() {
        {
            Country mockCountry = new Country();
            mockCountry.setIsoCode("DO");
            mockCountry.setCountryIdependenceDate("27/02/1844");
            mockCountry.setCountryId(1L);
            mockCountry.setCountryName("Republica Dominicana");
            mockCountry.setCountryCapital("Santo Domingo");
            Mockito.when(countryRepositoryMock.findCountryByIsoCode("DO")).thenReturn(mockCountry);
        }

        ResponseEntity<CountryResponse> response = independencyController.getCountryDetails("DO");

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Republica Dominicana", response.getBody().getCountryName());
    }

    @Test
    void getCountryDetailsWithInvalidCountryCode() {
        Mockito.when(countryRepositoryMock.findCountryByIsoCode("IT")).thenReturn(null);
        ResponseEntity<CountryResponse> response = independencyController.getCountryDetails("IT");

        Assertions.assertNull(response.getBody());
    }
}
