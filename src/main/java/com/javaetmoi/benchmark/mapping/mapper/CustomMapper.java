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
        orderDTO.setCustomerName(getCustomer(customer).getName());

        Optional<Address> optBillingAddress = customer.flatMap(Customer::getBillingAddress);
        orderDTO.setBillingAlphaCode2(getCode(optBillingAddress));

        Address billingAddress = optBillingAddress.orElse(ADDRESS);
        orderDTO.setBillingCity(billingAddress.getCity());
        orderDTO.setBillingStreetAddress(billingAddress.getStreet());

        Optional<Address> optShippingAddress = customer.flatMap(Customer::getShippingAddress);
        orderDTO.setShippingAlphaCode2(getCode(optShippingAddress));

        Address shippingAddress = optShippingAddress.orElse(ADDRESS);
        orderDTO.setShippingCity(shippingAddress.getCity());
        orderDTO.setShippingStreetAddress(shippingAddress.getStreet());
    }

    private Customer getCustomer(Optional<Customer> customer) {
        return customer.orElse(CUSTOMER);
    }

    private String getCode(Optional<Address> address) {
        return address.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).orElse(ALPHA_CODE_2).getCode();
    }
}
