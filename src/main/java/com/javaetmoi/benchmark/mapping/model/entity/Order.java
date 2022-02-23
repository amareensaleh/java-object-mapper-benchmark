package com.javaetmoi.benchmark.mapping.model.entity;

import java.util.List;
import optional4j.spec.Absent;
import optional4j.spec.Optional;
import optional4j.spec.Present;

public class Order implements Present<Order> {
    private Customer customer;
    private List<Product> products;

    public Optional<Customer> getCustomer() {
        return customer != null ? customer: Absent.nothing();
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