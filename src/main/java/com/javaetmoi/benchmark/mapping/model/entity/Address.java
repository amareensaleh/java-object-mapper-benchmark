package com.javaetmoi.benchmark.mapping.model.entity;

import optional4j.spec.Optional;
import optional4j.spec.Present;

public class Address implements Present<Address> {

    private Country country;

    private String street;

    private String city;

    public Optional<Country> getCountry() {
        return Optional.ofNullable(country);
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}