package com.javaetmoi.benchmark.mapping.model.entity;

import optional4j.annotation.ValueType;

@ValueType
public class Product {
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