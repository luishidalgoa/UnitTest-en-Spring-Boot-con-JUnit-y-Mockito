package com.tutorialesvip.tutorialunittest.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class DiferenciaEntreFechasTest {
    DiferenciaEntreFechas diferenciaEntreFechas;

    @Test
    void calculateYearsOfIndependency() {
        diferenciaEntreFechas = new DiferenciaEntreFechas();
        String fechaIndependencia = "27/02/1844";

        // Ejecutar método bajo prueba
        Period resultado = diferenciaEntreFechas.calculateYearsOfIndependency(fechaIndependencia);

        // Calcular manualmente el valor esperado con la misma lógica que usaría el método
        LocalDate fechaIndep = LocalDate.of(1844, 2, 27);
        LocalDate hoy = LocalDate.now();
        Period esperado = Period.between(fechaIndep, hoy);

        // Validar que el resultado coincide con lo esperado
        assertEquals(esperado.getYears(), resultado.getYears());
        assertEquals(esperado.getMonths(), resultado.getMonths());
        assertEquals(esperado.getDays(), resultado.getDays());
        System.out.printf("Esperado: %d años, %d meses, %d días%n",
                esperado.getYears(), esperado.getMonths(), esperado.getDays());

    }

}