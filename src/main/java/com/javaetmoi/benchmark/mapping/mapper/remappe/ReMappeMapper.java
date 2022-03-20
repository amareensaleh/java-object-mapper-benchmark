package com.javaetmoi.benchmark.mapping.mapper.remappe;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class ReMappeMapper implements OrderMapper {

    private final Mapper<Product, ProductDTO> mapperToDtoProduct = Mapping
        .from(Product.class)
        .to(ProductDTO.class)
        .mapper();

    private final Mapper<Order, OrderDTO> mapperToDto = Mapping
        .from(Order.class)
        .to(OrderDTO.class)
        .replace(Order::getCustomer, OrderDTO::getCustomerName).withSkipWhenNull(customer -> customer.map(Customer::getName).orElse(null))
        .replace(Order::getCustomer, OrderDTO::getBillingStreetAddress).withSkipWhenNull(customer -> customer.flatMap(Customer::getBillingAddress).map(Address::getStreet).orElse(null))
        .replace(Order::getCustomer, OrderDTO::getBillingCity).withSkipWhenNull(customer -> customer.flatMap(Customer::getBillingAddress).map(Address::getCity).orElse(null))
        .replace(Order::getCustomer, OrderDTO::getShippingStreetAddress).withSkipWhenNull(customer -> customer.flatMap(Customer::getShippingAddress).map(Address::getStreet).orElse(null))
        .replace(Order::getCustomer, OrderDTO::getShippingCity).withSkipWhenNull(customer -> customer.flatMap(Customer::getShippingAddress).map(Address::getCity).orElse(null))
        .useMapper(mapperToDtoProduct)
        .mapper();

    private String getAlphaCode2(Address address) {
        Country country = address.getCountry();
        if (country != null) {
            IsoCode isoCode = country.getIsoCode();
            if (isoCode != null) {
                AlphaCode2 alphaCode2 = isoCode.getAlphaCode2();
                if (alphaCode2 != null) {
                    return alphaCode2.getCode();
                }
            }
        }
        return null;
    }

    @Override
    public OrderDTO map(Order source) {
        return mapperToDto.map(source);
    }

}
