package com.javaetmoi.benchmark.mapping.mapper.orika;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import java.util.Optional;

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

                        Optional<Customer> customer = order.getCustomer();

                        customer.ifPresent(aCustomer -> orderDTO.setCustomerName(aCustomer.getName()));

                        customer.flatMap(Customer::getShippingAddress)
                                .ifPresent(address -> {
                                    orderDTO.setShippingCity(address.getCity());
                                    orderDTO.setShippingStreetAddress(address.getStreet());
                                });

                        customer.flatMap(Customer::getBillingAddress)
                                .ifPresent(address -> {
                                    orderDTO.setBillingCity(address.getCity());
                                    orderDTO.setBillingStreetAddress(address.getStreet());
                                });
                    }
                })
                .toClassMap());
        orderMapper = factory.getMapperFacade(Order.class, OrderDTO.class, false);
    }

    @Override
    public OrderDTO map(Order source) {
        return orderMapper.map(source);
    }
};

