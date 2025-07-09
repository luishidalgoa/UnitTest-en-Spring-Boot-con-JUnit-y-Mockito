package com.tutorialesvip.tutorialunittest.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DiferenciaEntreFechas {

    public static Period calculateYearsOfIndependency(String independenceDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        LocalDate localDate = LocalDate.parse(independenceDay, formatter);
        LocalDate today = LocalDate.now();
        return Period.between(localDate, today);
    }
}