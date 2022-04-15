package com.javaetmoi.benchmark.mapping.mapper;

import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import java.util.Optional;

public class CustomMapper {

    public static final CustomMapper CUSTOM_MAPPER = new CustomMapper();

    public void map(Order order, OrderDTO orderDTO) {

        Optional<Customer> customer = order.getCustomer();
        orderDTO.setCustomerName(customer.map(Customer::getName).orElse(null));

        Optional<Address> optBillingAddress = customer.flatMap(Customer::getBillingAddress);
        orderDTO.setBillingAlphaCode2(optBillingAddress.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).map(AlphaCode2::getCode).orElse(null));
        orderDTO.setBillingCity(optBillingAddress.map(Address::getCity).orElse(null));
        orderDTO.setBillingStreetAddress(optBillingAddress.map(Address::getStreet).orElse(null));

        Optional<Address> optShippingAddress = customer.flatMap(Customer::getShippingAddress);
        orderDTO.setShippingAlphaCode2(optShippingAddress.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).map(AlphaCode2::getCode).orElse(null));
        orderDTO.setShippingCity(optShippingAddress.map(Address::getCity).orElse(null));
        orderDTO.setShippingStreetAddress(optShippingAddress.map(Address::getStreet).orElse(null));
    }
}
