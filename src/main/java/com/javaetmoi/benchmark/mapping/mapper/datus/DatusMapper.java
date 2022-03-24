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

    private static final Customer CUSTOMER = new Customer();
    private static final Address ADDRESS = new Address();
    private static final AlphaCode2 ALPHA_CODE_2 = new AlphaCode2();

    private static final Mapper<Product, ProductDTO> productMapper = Datus.forTypes(Product.class, ProductDTO.class)
            .immutable((String name) -> new ProductDTO(name))
            .from(Product::getName).to(ConstructorParameter::bind)
            .build();

    private static final Mapper<Order, OrderDTO> orderMapper = Datus.forTypes(Order.class, OrderDTO.class)
            .mutable(OrderDTO::new)

            .from(Order::getCustomer)
            .map(customer -> customer.orElse(CUSTOMER).getName()).into(OrderDTO::setCustomerName)

            .from(Order::getCustomer)
            .map(customer -> customer.flatMap(Customer::getBillingAddress).orElse(ADDRESS).getCity())
            .into(OrderDTO::setBillingCity)

            .from(Order::getCustomer)
            .map(customer -> customer.flatMap(Customer::getBillingAddress).orElse(ADDRESS).getStreet())
            .into(OrderDTO::setBillingStreetAddress)

            .from(Order::getCustomer)
            .map(customer -> customer.flatMap(Customer::getShippingAddress).orElse(ADDRESS).getCity())
            .into(OrderDTO::setShippingCity)

            .from(Order::getCustomer)
            .map(customer -> customer.flatMap(Customer::getShippingAddress).orElse(ADDRESS).getStreet())
            .into(OrderDTO::setShippingStreetAddress)

            .from(Order::getCustomer)
            .map(customer -> customer.flatMap(Customer::getShippingAddress).flatMap(Address::getCountry)
                    .flatMap(Country::getIsoCode)
                    .flatMap(IsoCode::getAlphaCode2)
                    .orElse(ALPHA_CODE_2)
                    .getCode())
            .into(OrderDTO::setShippingAlphaCode2)

            .from(Order::getCustomer)
            .map(customer -> customer.flatMap(Customer::getBillingAddress).flatMap(Address::getCountry)
                    .flatMap(Country::getIsoCode)
                    .flatMap(IsoCode::getAlphaCode2)
                    .orElse(ALPHA_CODE_2)
                    .getCode())
            .into(OrderDTO::setBillingAlphaCode2)

            .from(Order::getProducts).nullsafe()
            .map(productMapper::convert).into(OrderDTO::setProducts)
            .build();

    @Override
    public OrderDTO map(Order source) {
        return orderMapper.convert(source);
    }
}
