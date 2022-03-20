package com.javaetmoi.benchmark.mapping.model.dto;

import java.util.List;

public class OrderDTO {

	private List<ProductDTO> products;

    private String customerName;

    private String shippingStreetAddress;

    private String shippingCity;

    private String billingStreetAddress;

    private String billingCity;

    private String shippingAlphaCode2;

    private String billingAlphaCode2;

    public String getShippingAlphaCode2() {
        return shippingAlphaCode2;
    }

    public void setShippingAlphaCode2(String shippingAlphaCode2) {
        this.shippingAlphaCode2 = shippingAlphaCode2;
    }

    public String getBillingAlphaCode2() {
        return billingAlphaCode2;
    }

    public void setBillingAlphaCode2(String billingAlphaCode2) {
        this.billingAlphaCode2 = billingAlphaCode2;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShippingStreetAddress() {
        return shippingStreetAddress;
    }

    public void setShippingStreetAddress(String shippingStreetAddress) {
        this.shippingStreetAddress = shippingStreetAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    public void setBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}