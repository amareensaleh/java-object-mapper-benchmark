package com.javaetmoi.benchmark.mapping.mapper;

import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import optional4j.spec.Optional;

public class CustomMapper {

    public static final CustomMapper CUSTOM_MAPPER = new CustomMapper();

    private static final Customer CUSTOMER = new Customer();
    private static final Address ADDRESS = new Address();
    private static final AlphaCode2 ALPHA_CODE_2 = new AlphaCode2();

    public void map(Order order, OrderDTO orderDTO) {

        Optional<Customer> customer = order.getCustomer();
        orderDTO.setCustomerName(customer.orElse(CUSTOMER).getName());

        Optional<Address> optBillingAddress = customer.flatMap(Customer::getBillingAddress);
        orderDTO.setBillingAlphaCode2(optBillingAddress.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).orElse(ALPHA_CODE_2).getCode());

        Address billingAddress = optBillingAddress.orElse(ADDRESS);
        orderDTO.setBillingCity(billingAddress.getCity());
        orderDTO.setBillingStreetAddress(billingAddress.getStreet());

        Optional<Address> optShippingAddress = customer.flatMap(Customer::getShippingAddress);
        orderDTO.setShippingAlphaCode2(optShippingAddress.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).orElse(ALPHA_CODE_2).getCode());

        Address shippingAddress = optShippingAddress.orElse(ADDRESS);
        orderDTO.setShippingCity(shippingAddress.getCity());
        orderDTO.setShippingStreetAddress(shippingAddress.getStreet());
    }
}
