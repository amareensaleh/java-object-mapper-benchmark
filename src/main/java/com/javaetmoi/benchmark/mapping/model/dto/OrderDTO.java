package com.javaetmoi.benchmark.mapping.model.dto;

import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import java.util.List;
import com.google.common.base.Optional;

public class OrderDTO {

    @JMap("products")
	private List<ProductDTO> products;

    @JMap("customer")
    private String customerName;

    @JMap("customer")
    private String shippingStreetAddress;

    @JMap("customer")
    private String shippingCity;

    @JMap("customer")
    private String billingStreetAddress;

    @JMap("customer")
    private String billingCity;

    @JMap("customer")
    private String shippingAlphaCode2;

    @JMap("customer")
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

    @JMapConversion(from={"customer"}, to={"customerName"})
    public String toCustomerName(Optional<Customer> customer) {
        return customer.transform(Customer::getName).orNull();
    }

    @JMapConversion(from={"customer"}, to={"billingStreetAddress"})
    public String toBillingStreetAddress(Optional<Customer> customer){
        return customer.transform(Customer::getBillingAddress).transform(Address::getStreet).orNull();
    }

    @JMapConversion(from={"customer"}, to={"shippingStreetAddress"})
    public String toShippingStreetAddress(Optional<Customer> customer){
        return customer.transform(Customer::getShippingAddress).transform(Address::getStreet).orNull();
    }

    @JMapConversion(from={"customer"}, to={"shippingAlphaCode2"})
    public String toShippingAlphaCode2(Optional<Customer> customer){
        return customer.transform(Customer::getShippingAddress)
                .transform(Address::getCountry)
                .transform(Country::getIsoCode)
                .transform(IsoCode::getAlphaCode2)
                .transform(AlphaCode2::getCode)
                .orNull();
    }

    @JMapConversion(from={"customer"}, to={"billingAlphaCode2"})
    public String toBillingAlphaCode2(Optional<Customer> customer){
        return customer.transform(Customer::getBillingAddress)
                .transform(Address::getCountry)
                .transform(Country::getIsoCode)
                .transform(IsoCode::getAlphaCode2)
                .transform(AlphaCode2::getCode)
                .orNull();
    }

    @JMapConversion(from={"customer"}, to={"billingCity"})
    public String toBillingCity(Optional<Customer> customer){
        return customer.transform(Customer::getBillingAddress).transform(Address::getCity).orNull();
    }

    @JMapConversion(from={"customer"}, to={"shippingCity"})
    public String toShippingCity(Optional<Customer> customer){
        return customer.transform(Customer::getShippingAddress).transform(Address::getCity).orNull();
    }
}