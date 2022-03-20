package com.javaetmoi.benchmark.mapping.mapper.modelmapper;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import org.modelmapper.PropertyMap;

public class ModelMapper implements OrderMapper {

    private org.modelmapper.ModelMapper modelMapper;

    public ModelMapper() {
        modelMapper = new org.modelmapper.ModelMapper();
        modelMapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
                map().setBillingStreetAddress(source.getCustomer().getBillingAddress().getStreet());
                map().setBillingCity(source.getCustomer().getBillingAddress().getCity());
                map().setShippingStreetAddress(source.getCustomer().getShippingAddress().getStreet());
                map().setShippingCity(source.getCustomer().getShippingAddress().getCity());
                map().setShippingAlphaCode2(source.getCustomer().getShippingAddress().getCountry().getIsoCode().getAlphaCode2().getCode());
                map().setBillingAlphaCode2(source.getCustomer().getBillingAddress().getCountry().getIsoCode().getAlphaCode2().getCode());
            }
        });
    }

    @Override
    public OrderDTO map(Order source) {
        return modelMapper.map(source, OrderDTO.class);
    }
}
