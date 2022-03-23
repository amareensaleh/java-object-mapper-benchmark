package com.javaetmoi.benchmark.mapping.model.entity;

import java.util.List;
import java.util.Optional;

public class Order {

    private Customer customer;

    private List<Product> products;

    public Optional<Customer> getCustomer() {
        return Optional.ofNullable(customer);
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