package com.javaetmoi.benchmark.mapping.mapper.datus;

import com.github.roookeee.datus.api.Datus;
import com.github.roookeee.datus.api.Mapper;
import com.github.roookeee.datus.immutable.ConstructorParameter;
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

public class DatusMapper implements OrderMapper {

    private static final Mapper<Product, ProductDTO> productMapper = Datus.forTypes(Product.class, ProductDTO.class)
            .immutable((String name) -> new ProductDTO(name))
            .from(Product::getName).to(ConstructorParameter::bind)
            .build();

    private static final Mapper<Order, OrderDTO> orderMapper = Datus.forTypes(Order.class, OrderDTO.class)
            .mutable(OrderDTO::new)

            .from(Order::getCustomer)
            .map(customer -> customer.transform(Customer::getName).orNull()).into(OrderDTO::setCustomerName)

            .from(Order::getCustomer)
            .map(customer -> customer.transform(Customer::getBillingAddress).transform(Address::getCity).orNull())
            .into(OrderDTO::setBillingCity)

            .from(Order::getCustomer)
            .map(customer -> customer.transform(Customer::getBillingAddress).transform(Address::getStreet).orNull())
            .into(OrderDTO::setBillingStreetAddress)

            .from(Order::getCustomer)
            .map(customer -> customer.transform(Customer::getShippingAddress).transform(Address::getCity).orNull())
            .into(OrderDTO::setShippingCity)

            .from(Order::getCustomer)
            .map(customer -> customer.transform(Customer::getShippingAddress).transform(Address::getStreet).orNull())
            .into(OrderDTO::setShippingStreetAddress)

            .from(Order::getCustomer)
            .map(customer -> customer.transform(Customer::getShippingAddress).transform(Address::getCountry)
                    .transform(Country::getIsoCode)
                    .transform(IsoCode::getAlphaCode2)
                    .transform(AlphaCode2::getCode)
                    .orNull())
            .into(OrderDTO::setShippingAlphaCode2)

            .from(Order::getCustomer)
            .map(customer -> customer.transform(Customer::getBillingAddress).transform(Address::getCountry)
                    .transform(Country::getIsoCode)
                    .transform(IsoCode::getAlphaCode2)
                    .transform(AlphaCode2::getCode)
                    .orNull())
            .into(OrderDTO::setBillingAlphaCode2)

            .from(Order::getProducts).nullsafe()
            .map(productMapper::convert).into(OrderDTO::setProducts)
            .build();

    @Override
    public OrderDTO map(Order source) {
        return orderMapper.convert(source);
    }
}
