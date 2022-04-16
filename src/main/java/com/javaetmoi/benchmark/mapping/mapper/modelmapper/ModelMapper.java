package com.javaetmoi.benchmark.mapping.mapper.modelmapper;

import com.javaetmoi.benchmark.mapping.mapper.CustomMapper;
import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class ModelMapper implements OrderMapper {

    private static final Converter<Order, OrderDTO> POST_CONVERTER = mappingContext -> {

        OrderDTO orderDTO = mappingContext.getDestination();

        CustomMapper.CUSTOM_MAPPER.map(mappingContext.getSource(), orderDTO);

        return orderDTO;
    };

    private final org.modelmapper.ModelMapper mapper;

    public ModelMapper() {
        mapper = new org.modelmapper.ModelMapper();
        mapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {

            }
        }).setPostConverter(POST_CONVERTER);
    }

    @Override
    public OrderDTO map(Order source) {
        return mapper.map(source, OrderDTO.class);
    }
}
