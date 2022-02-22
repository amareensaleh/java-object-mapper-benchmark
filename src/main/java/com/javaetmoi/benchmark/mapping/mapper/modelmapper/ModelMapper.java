package com.javaetmoi.benchmark.mapping.mapper.modelmapper;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class ModelMapper implements OrderMapper {

    private static final Converter<Order, OrderDTO> POST_CONVERTER = mappingContext -> {
        OrderDTO orderDTO = mappingContext.getDestination();

        mappingContext.getSource().getCustomer()
                .ifPresent(customer -> {
                    orderDTO.setCustomerName(customer.getName());
                    orderDTO.setShippingStreetAddress(customer.getShippingAddress().map(Address::getStreet).orElse(null));
                    orderDTO.setShippingCity(customer.getShippingAddress().map(Address::getCity).orElse(null));
                    orderDTO.setBillingStreetAddress(customer.getBillingAddress().map(Address::getStreet).orElse(null));
                    orderDTO.setBillingCity(customer.getBillingAddress().map(Address::getCity).orElse(null));
                });


        return orderDTO;
    };

    private org.modelmapper.ModelMapper modelMapper;

    public ModelMapper() {
        modelMapper = new org.modelmapper.ModelMapper();
        modelMapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
            }
        }).setPostConverter(POST_CONVERTER);
    }

    @Override
    public OrderDTO map(Order source) {
        return modelMapper.map(source, OrderDTO.class);
    }
}
