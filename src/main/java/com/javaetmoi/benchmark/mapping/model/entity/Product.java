package com.javaetmoi.benchmark.mapping.model.entity;

import optional4j.spec.Present;

public class Product implements Present<Product> {
    private String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}