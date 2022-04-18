package com.javaetmoi.benchmark.mapping.mapper.remappe;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;


public final class ReMappeMapper implements OrderMapper {

    private final Mapper<Product, ProductDTO> mapperToDtoProduct = Mapping
        .from(Product.class)
        .to(ProductDTO.class)
        .mapper();

    private final Mapper<Order, OrderDTO> mapperToDto = Mapping
        .from(Order.class)
        .to(OrderDTO.class)
        .replace(Order::getCustomer, OrderDTO::getCustomerName).with(customer -> customer.transform(Customer::getName).orNull())
        .replace(Order::getCustomer, OrderDTO::getBillingStreetAddress).with(customer -> customer.transform(Customer::getBillingAddress).transform(Address::getStreet).orNull())
        .replace(Order::getCustomer, OrderDTO::getBillingCity).with(customer -> customer.transform(Customer::getBillingAddress).transform(Address::getCity).orNull())
        .replace(Order::getCustomer, OrderDTO::getShippingStreetAddress).with(customer -> customer.transform(Customer::getShippingAddress).transform(Address::getStreet).orNull())
        .replace(Order::getCustomer, OrderDTO::getShippingCity).with(customer -> customer.transform(Customer::getShippingAddress).transform(Address::getCity).orNull())
        .replace(Order::getCustomer, OrderDTO::getShippingAlphaCode2).with(customer -> customer.transform(Customer::getShippingAddress).transform(Address::getCountry)
                    .transform(Country::getIsoCode)
                    .transform(IsoCode::getAlphaCode2)
                    .transform(AlphaCode2::getCode)
                    .orNull())
        .replace(Order::getCustomer, OrderDTO::getBillingAlphaCode2).with(customer -> customer.transform(Customer::getBillingAddress).transform(Address::getCountry)
                    .transform(Country::getIsoCode)
                    .transform(IsoCode::getAlphaCode2)
                    .transform(AlphaCode2::getCode)
                    .orNull())
        .useMapper(mapperToDtoProduct)
        .mapper();

    @Override
    public OrderDTO map(Order source) {
        return mapperToDto.map(source);
    }

}
