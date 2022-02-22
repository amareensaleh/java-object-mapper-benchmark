package com.javaetmoi.benchmark.mapping.mapper.datus;

import com.github.roookeee.datus.api.Datus;
import com.github.roookeee.datus.api.Mapper;
import com.github.roookeee.datus.immutable.ConstructorParameter;
import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;

public class DatusMapper implements OrderMapper {
    private static final Mapper<Product, ProductDTO> productMapper = Datus.forTypes(Product.class, ProductDTO.class)
            .immutable((String name) -> new ProductDTO(name))
            .from(Product::getName).to(ConstructorParameter::bind)
            .build();

    private static final Mapper<Order, OrderDTO> orderMapper = Datus.forTypes(Order.class, OrderDTO.class)
            .mutable(OrderDTO::new)
            .from(Order::getCustomer).nullsafe()
            .map(customer -> customer.map(Customer::getName).orElse(null)).into(OrderDTO::setCustomerName)
            .from(Order::getCustomer).nullsafe()
            .map(customer -> customer.flatMap(Customer::getBillingAddress))
            .map(address -> address.map(Address::getCity).orElse(null)).into(OrderDTO::setBillingCity)
            .from(Order::getCustomer).nullsafe()
            .map(customer -> customer.flatMap(Customer::getBillingAddress))
            .map(address -> address.map(Address::getStreet).orElse(null)).into(OrderDTO::setBillingStreetAddress)
            .from(Order::getCustomer).nullsafe()
            .map(customer -> customer.flatMap(Customer::getShippingAddress))
            .map(address -> address.map(Address::getCity).orElse(null)).into(OrderDTO::setShippingCity)
            .from(Order::getCustomer).nullsafe()
            .map(customer -> customer.flatMap(Customer::getShippingAddress))
            .map(address -> address.map(Address::getStreet).orElse(null)).into(OrderDTO::setShippingStreetAddress)
            .from(Order::getProducts).nullsafe()
            .map(productMapper::convert).into(OrderDTO::setProducts)
            .build();

    @Override
    public OrderDTO map(Order source) {
        return orderMapper.convert(source);
    }
}
