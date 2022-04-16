package com.javaetmoi.benchmark.mapping.mapper.orika;

import static com.javaetmoi.benchmark.mapping.mapper.CustomMapper.CUSTOM_MAPPER;
import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * Using custom BoundMapperFacade with no object graph cycles.
 *
 * @see <a href="http://orika-mapper.github.io/orika-docs/performance-tuning.html">http://orika-mapper.github.io/orika-docs/performance-tuning.html</a>
 */
public class OrikaMapper implements OrderMapper {

    private final BoundMapperFacade<Order, OrderDTO> mapper;

    public OrikaMapper() {
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        factory.registerClassMap(factory.classMap(Order.class, OrderDTO.class)
                .field("products", "products")
                .customize(new CustomMapper<Order, OrderDTO>() {
                    @Override
                    public void mapAtoB(Order order, OrderDTO orderDTO, MappingContext context) {
                        CUSTOM_MAPPER.map(order, orderDTO);
                    }
                })
                .toClassMap());
        mapper = factory.getMapperFacade(Order.class, OrderDTO.class, false);
    }

    @Override
    public OrderDTO map(Order source) {
        return mapper.map(source);
    }
};

