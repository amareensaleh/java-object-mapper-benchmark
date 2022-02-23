package com.javaetmoi.benchmark.mapping.model.entity;


import optional4j.spec.Absent;
import optional4j.spec.Optional;
import optional4j.spec.Present;

public class Customer implements Present<Customer> {
    String name;
    Address shippingAddress;
    Address billingAddress;

    public Optional<Address> getShippingAddress() {
        return shippingAddress != null ? shippingAddress: Absent.nothing();
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Optional<Address> getBillingAddress() {
        return billingAddress != null ? billingAddress: Absent.nothing();
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