package com.javaetmoi.benchmark.mapping.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderFactory {

    public static Order buildOrder() {

        Order order = new Order();

        Customer customer = new Customer();
        order.setCustomer(customer);
        customer.setName("Joe Smith");

        Address billingAddress = new Address();
        customer.setBillingAddress(billingAddress);
        billingAddress.setStreet("1234 Market Street");
        billingAddress.setCity("San Fran");
        billingAddress.setCountry(getCountry("US"));

        Address shippingAddress = new Address();
        customer.setShippingAddress(shippingAddress);
        shippingAddress.setStreet("1234 West Townsend");
        shippingAddress.setCity("Boston");
        shippingAddress.setCountry(getCountry("US"));

        List<Product> products = new ArrayList<Product>(2);
        order.setProducts(products);
        products.add(new Product("socks"));
        products.add(new Product("shoes"));
        return order;
    }

    private static Country getCountry(String code) {
        Country country = new Country();
        IsoCode isoCode = new IsoCode();
        AlphaCode2 alphaCode2 = new AlphaCode2();
        alphaCode2.setCode(code);
        isoCode.setAlphaCode2(null);
        country.setIsoCode(isoCode);
        return country;
    }

    public static Order buildPartialOrder() {
        Order order = new Order();
        Customer customer = new Customer();
        order.setCustomer(customer);
        customer.setName("John Doe");
        Address billingAddress = new Address();
        customer.setBillingAddress(billingAddress);
        billingAddress.setStreet("93 Newcastle Dr.");
        order.setProducts(Collections.emptyList());
        return order;
    }
}
