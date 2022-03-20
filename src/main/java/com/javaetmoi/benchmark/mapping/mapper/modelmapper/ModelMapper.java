package com.javaetmoi.benchmark.mapping.mapper.modelmapper;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import optional4j.spec.Optional;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class ModelMapper implements OrderMapper {

    private static final Converter<Order, OrderDTO> POST_CONVERTER = mappingContext -> {

        OrderDTO orderDTO = mappingContext.getDestination();

        mappingContext.getSource().getCustomer().ifPresent(aCustomer -> {

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
