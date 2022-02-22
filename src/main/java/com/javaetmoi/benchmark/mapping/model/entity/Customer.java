package com.javaetmoi.benchmark.mapping.model.entity;

import optional4j.annotation.OptionalReturn;
import optional4j.annotation.ValueType;

@ValueType
public class Customer {
    String name;
    Address shippingAddress;
    Address billingAddress;

    @OptionalReturn
    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @OptionalReturn
    public Address getBillingAddress() {
        return billingAddress;
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