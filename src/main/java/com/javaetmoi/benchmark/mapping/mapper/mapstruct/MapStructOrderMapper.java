package com.javaetmoi.benchmark.mapping.mapper.mapstruct;

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
import com.google.common.base.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper
public interface MapStructOrderMapper extends OrderMapper {

    @Mappings({
            @Mapping(source = "customer", target = "customerName", qualifiedByName = "unwrapCustomerName"),
            @Mapping(source = "customer", target = "billingStreetAddress", qualifiedByName = "unwrapBillingStreet"),
            @Mapping(source = "customer", target = "billingCity", qualifiedByName = "unwrapBillingCity"),
            @Mapping(source = "customer", target = "shippingStreetAddress", qualifiedByName = "unwrapShippingStreet"),
            @Mapping(source = "customer", target = "shippingCity", qualifiedByName = "unwrapShippingCity"),
            @Mapping(source = "customer", target = "shippingAlphaCode2", qualifiedByName = "unwrapShippingAlphaCode2"),
            @Mapping(source = "customer", target = "billingAlphaCode2", qualifiedByName = "unwrapBillingAlphaCode2"),
    })
    OrderDTO map(Order source);

    @Mapping(source = "name", target = "name")
    ProductDTO productToProductDTO(Product product);

    @Named("unwrapBillingStreet")
    default String unwrapBillingStreet(Optional<Customer> customer){
        return customer.transform(Customer::getBillingAddress).transform(Address::getStreet).orNull();
    }

    @Named("unwrapShippingStreet")
    default String unwrapShippingStreet(Optional<Customer> customer){
        return customer.transform(Customer::getShippingAddress).transform(Address::getStreet).orNull();
    }

    @Named("unwrapShippingAlphaCode2")
    default String unwrapShippingAlphaCode2(Optional<Customer> customer){
        return customer.transform(Customer::getShippingAddress)
                .transform(Address::getCountry)
                .transform(Country::getIsoCode)
                .transform(IsoCode::getAlphaCode2)
                .transform(AlphaCode2::getCode)
                .orNull();
    }

    @Named("unwrapBillingAlphaCode2")
    default String unwrapBillingAlphaCode2(Optional<Customer> customer){
        return customer.transform(Customer::getBillingAddress)
                .transform(Address::getCountry)
                .transform(Country::getIsoCode)
                .transform(IsoCode::getAlphaCode2)
                .transform(AlphaCode2::getCode)
                .orNull();
    }

    @Named("unwrapBillingCity")
    default String unwrapBillingCity(Optional<Customer> customer){
        return customer.transform(Customer::getBillingAddress).transform(Address::getCity).orNull();
    }

    @Named("unwrapShippingCity")
    default String unwrapShippingCity(Optional<Customer> customer){
        return customer.transform(Customer::getShippingAddress).transform(Address::getCity).orNull();
    }

    @Named("unwrapCustomerName")
    default String unwrapCustomerName(Optional<Customer> customer){
        return customer.transform(Customer::getName).orNull();
    }
}