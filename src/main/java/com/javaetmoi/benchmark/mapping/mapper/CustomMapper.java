package com.javaetmoi.benchmark.mapping.mapper;

import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.google.common.base.Optional;

public class CustomMapper {

    public static final CustomMapper CUSTOM_MAPPER = new CustomMapper();

    public void map(Order order, OrderDTO orderDTO) {

        Optional<Customer> customer = order.getCustomer();
        orderDTO.setCustomerName(customer.transform(Customer::getName).orNull());

        Optional<Address> optBillingAddress = customer.transform(Customer::getBillingAddress);
        orderDTO.setBillingAlphaCode2(optBillingAddress.transform(Address::getCountry).transform(Country::getIsoCode).transform(IsoCode::getAlphaCode2).transform(AlphaCode2::getCode).orNull());
        orderDTO.setBillingCity(optBillingAddress.transform(Address::getCity).orNull());
        orderDTO.setBillingStreetAddress(optBillingAddress.transform(Address::getStreet).orNull());

        Optional<Address> optShippingAddress = customer.transform(Customer::getShippingAddress);
        orderDTO.setShippingAlphaCode2(optShippingAddress.transform(Address::getCountry).transform(Country::getIsoCode).transform(IsoCode::getAlphaCode2).transform(AlphaCode2::getCode).orNull());
        orderDTO.setShippingCity(optShippingAddress.transform(Address::getCity).orNull());
        orderDTO.setShippingStreetAddress(optShippingAddress.transform(Address::getStreet).orNull());
    }
}
