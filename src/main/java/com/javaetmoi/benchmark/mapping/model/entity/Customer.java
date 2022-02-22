package com.javaetmoi.benchmark.mapping.model.entity;

import java.util.Optional;

public class Customer {
    String name;
    Address shippingAddress;
    Address billingAddress;

    public Optional<Address> getShippingAddress() {
        return Optional.ofNullable(shippingAddress);
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Optional<Address> getBillingAddress() {
        return Optional.ofNullable(billingAddress);
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}