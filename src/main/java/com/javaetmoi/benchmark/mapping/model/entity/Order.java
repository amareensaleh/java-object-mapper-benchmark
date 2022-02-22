package com.javaetmoi.benchmark.mapping.model.entity;

import java.util.List;
import optional4j.annotation.OptionalReturn;
import optional4j.annotation.ValueType;

@ValueType
public class Order {
    private Customer customer;
    private List<Product> products;

    @OptionalReturn
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}