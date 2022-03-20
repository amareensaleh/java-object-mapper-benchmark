package com.javaetmoi.benchmark.mapping.mapper.orika;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import optional4j.spec.Optional;

/**
 * Using custom BoundMapperFacade with no object graph cycles.
 *
 * @see <a href="http://orika-mapper.github.io/orika-docs/performance-tuning.html">http://orika-mapper.github.io/orika-docs/performance-tuning.html</a>
 */
public class OrikaMapper implements OrderMapper {

    private BoundMapperFacade<Order, OrderDTO> orderMapper;

    public OrikaMapper() {
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        factory.registerClassMap(factory.classMap(Order.class, OrderDTO.class)
                .field("products", "products")
                .customize(new CustomMapper<Order, OrderDTO>() {
                    @Override
                    public void mapAtoB(Order order, OrderDTO orderDTO, MappingContext context) {

                        order.getCustomer().ifPresent(aCustomer -> {

                            orderDTO.setCustomerName(aCustomer.getName());

                            aCustomer.getBillingAddress().ifPresent(address -> {
                                orderDTO.setBillingCity(address.getCity());
                                orderDTO.setBillingStreetAddress(address.getStreet());
                                address.getCountry().flatMap(Country::getIsoCode)
                                        .flatMap(IsoCode::getAlphaCode2)
                                        .ifPresent(alphaCode2 -> orderDTO.setBillingAlphaCode2(alphaCode2.getCode()));
                            });

                            aCustomer.getShippingAddress().ifPresent(address -> {
                                orderDTO.setShippingCity(address.getCity());
                                orderDTO.setShippingStreetAddress(address.getStreet());
                                address.getCountry().flatMap(Country::getIsoCode)
                                        .flatMap(IsoCode::getAlphaCode2)
                                        .ifPresent(alphaCode2 -> orderDTO.setShippingAlphaCode2(alphaCode2.getCode()));
                            });
                        });
                    }
                })
//                .field("customer.name", "customerName")
//                .field("customer.billingAddress.street",
//                        "billingStreetAddress")
//                .field("customer.billingAddress.city", "billingCity")
//                .field("customer.shippingAddress.street",
//                        "shippingStreetAddress")
//                .field("customer.shippingAddress.city",
//                        "shippingCity")
//                .field("products", "products")
//                .field("customer.billingAddress.country.isoCode.alphaCode2.code", "billingAlphaCode2")
//                .field("customer.shippingAddress.country.isoCode.alphaCode2.code", "shippingAlphaCode2")
                .toClassMap());
        orderMapper = factory.getMapperFacade(Order.class, OrderDTO.class, false);
    }

    @Override
    public OrderDTO map(Order source) {
        return orderMapper.map(source);
    }
};

