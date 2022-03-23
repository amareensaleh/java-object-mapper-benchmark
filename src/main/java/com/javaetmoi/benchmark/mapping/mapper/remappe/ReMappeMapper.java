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

    private static final Customer CUSTOMER = new Customer();
    private static final Address ADDRESS = new Address();
    private static final AlphaCode2 ALPHA_CODE_2 = new AlphaCode2();

    private final Mapper<Product, ProductDTO> mapperToDtoProduct = Mapping
        .from(Product.class)
        .to(ProductDTO.class)
        .mapper();

    private final Mapper<Order, OrderDTO> mapperToDto = Mapping
        .from(Order.class)
        .to(OrderDTO.class)
        .replace(Order::getCustomer, OrderDTO::getCustomerName).withSkipWhenNull(customer -> customer.orElse(CUSTOMER).getName())
        .replace(Order::getCustomer, OrderDTO::getBillingStreetAddress).withSkipWhenNull(customer -> customer.flatMap(Customer::getBillingAddress).orElse(ADDRESS).getStreet())
        .replace(Order::getCustomer, OrderDTO::getBillingCity).withSkipWhenNull(customer -> customer.flatMap(Customer::getBillingAddress).orElse(ADDRESS).getCity())
        .replace(Order::getCustomer, OrderDTO::getShippingStreetAddress).withSkipWhenNull(customer -> customer.flatMap(Customer::getShippingAddress).orElse(ADDRESS).getStreet())
        .replace(Order::getCustomer, OrderDTO::getShippingCity).withSkipWhenNull(customer -> customer.flatMap(Customer::getShippingAddress).orElse(ADDRESS).getCity())
        .replace(Order::getCustomer, OrderDTO::getShippingAlphaCode2).withSkipWhenNull(customer -> customer.flatMap(Customer::getShippingAddress).flatMap(Address::getCountry)
                    .flatMap(Country::getIsoCode)
                    .flatMap(IsoCode::getAlphaCode2)
                    .orElse(ALPHA_CODE_2)
                    .getCode())
        .replace(Order::getCustomer, OrderDTO::getBillingAlphaCode2).withSkipWhenNull(customer -> customer.flatMap(Customer::getBillingAddress).flatMap(Address::getCountry)
                    .flatMap(Country::getIsoCode)
                    .flatMap(IsoCode::getAlphaCode2)
                    .orElse(ALPHA_CODE_2)
                    .getCode())
        .useMapper(mapperToDtoProduct)
        .mapper();

    @Override
    public OrderDTO map(Order source) {
        return mapperToDto.map(source);
    }

}
