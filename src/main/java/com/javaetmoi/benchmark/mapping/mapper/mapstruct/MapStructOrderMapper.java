package com.javaetmoi.benchmark.mapping.mapper.mapstruct;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import optional4j.spec.Optional;
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
    })
    OrderDTO map(Order source);

    @Mapping(source = "name", target = "name")
    ProductDTO productToProductDTO(Product product);

    @Named("unwrapBillingStreet")
    default String unwrapBillingStreet(Optional<Customer> customer){
        return customer.flatMap(Customer::getBillingAddress).map(Address::getStreet).orElse(null);
    }

    @Named("unwrapShippingStreet")
    default String unwrapShippingStreet(Optional<Customer> customer){
        return customer.flatMap(Customer::getShippingAddress).map(Address::getStreet).orElse(null);
    }

    @Named("unwrapBillingCity")
    default String unwrapBillingCity(Optional<Customer> customer){
        return customer.flatMap(Customer::getBillingAddress).map(Address::getCity).orElse(null);
    }

    @Named("unwrapShippingCity")
    default String unwrapShippingCity(Optional<Customer> customer){
        return customer.flatMap(Customer::getShippingAddress).map(Address::getCity).orElse(null);
    }

    @Named("unwrapCustomerName")
    default String unwrapCustomerName(Optional<Customer> customer){
        return customer.map(Customer::getName).orElse(null);
    }
}