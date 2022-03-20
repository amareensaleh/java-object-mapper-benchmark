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

import com.remondis.remap.TypedSelector;
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
        .replace(Order::getCustomer, OrderDTO::getCustomerName).withSkipWhenNull(Customer::getName)
        .replace(Order::getCustomer, OrderDTO::getBillingStreetAddress).withSkipWhenNull(customer -> {
            Address billingAddress = customer.getBillingAddress();
            return Objects.nonNull(billingAddress)
                ? billingAddress.getStreet()
                : null;
        })
        .replace(Order::getCustomer, OrderDTO::getBillingCity).withSkipWhenNull(customer -> {
            Address billingAddress = customer.getBillingAddress();
            return Objects.nonNull(billingAddress)
                ? billingAddress.getCity()
                : null;
        })
        .replace(Order::getCustomer, OrderDTO::getShippingStreetAddress).withSkipWhenNull(customer -> {
            Address shippingAddress = customer.getShippingAddress();
            return Objects.nonNull(shippingAddress)
                ? shippingAddress.getStreet()
                : null;
        })
        .replace(Order::getCustomer, OrderDTO::getShippingCity).withSkipWhenNull(customer -> {
            Address shippingAddress = customer.getShippingAddress();
            return Objects.nonNull(shippingAddress)
                ? shippingAddress.getCity()
                : null;
        })
        .replace(Order::getCustomer, OrderDTO::getShippingAlphaCode2).withSkipWhenNull(customer -> {
                Address shippingAddress = customer.getShippingAddress();
                return Objects.nonNull(shippingAddress)
                        ? getAlphaCode2(shippingAddress)
                        : null;
            })
        .replace(Order::getCustomer, OrderDTO::getBillingAlphaCode2).withSkipWhenNull(customer -> {
                Address billingAddress = customer.getBillingAddress();
                return Objects.nonNull(billingAddress)
                        ? getAlphaCode2(billingAddress)
                        : null;
            })
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
