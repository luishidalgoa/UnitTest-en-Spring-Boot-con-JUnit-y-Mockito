package com.tutorialesvip.tutorialunittest.models;

import com.tutorialesvip.tutorialunittest.util.DiferenciaEntreFechas;import java.time.Period;public class CountryResponse {
    private String countryName;
    private String capitalName;
    private String independenceDate;
    private int yearsOfIndependency;
    private int monthsOfIndependency;
    private int dayssOfIndependency;

    public CountryResponse(Country country) {
        countryName = country.getCountryName();
        capitalName = country.getCountryCapital();
        independenceDate = country.getCountryIdependenceDate();
        Period period = DiferenciaEntreFechas.calculateYearsOfIndependency(country.getCountryIdependenceDate());
        yearsOfIndependency = period.getYears();
        monthsOfIndependency = period.getMonths();
        dayssOfIndependency = period.getDays();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public String getIndependenceDate() {
        return independenceDate;
    }

    public void setIndependenceDate(String independenceDate) {
        this.independenceDate = independenceDate;
    }

    public int getYearsOfIndependency() {
        return yearsOfIndependency;
    }

    public void setYearsOfIndependency(int yearsOfIndependency) {
        this.yearsOfIndependency = yearsOfIndependency;
    }

    public int getMonthsOfIndependency() {
        return monthsOfIndependency;
    }

    public void setMonthsOfIndependency(int monthsOfIndependency) {
        this.monthsOfIndependency = monthsOfIndependency;
    }

    public int getDayssOfIndependency() {
        return dayssOfIndependency;
    }

    public void setDayssOfIndependency(int dayssOfIndependency) {
        this.dayssOfIndependency = dayssOfIndependency;
    }
}
