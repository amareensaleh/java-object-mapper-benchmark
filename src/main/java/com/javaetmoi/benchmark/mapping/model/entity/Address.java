package com.javaetmoi.benchmark.mapping.model.entity;

import optional4j.spec.Present;

public class Address implements Present<Address> {
    String street;
    String city;

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