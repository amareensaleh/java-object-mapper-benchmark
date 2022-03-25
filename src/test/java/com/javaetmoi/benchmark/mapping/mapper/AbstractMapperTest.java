package com.javaetmoi.benchmark.mapping.mapper;


import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.OrderFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractMapperTest {

    @Test
    public void map_with_all_fields() {
        Order order = OrderFactory.buildOrder();
        OrderDTO orderDTO = testedOrderMapper().map(order);
        assertEquals("Joe Smith", orderDTO.getCustomerName());
        assertEquals("1234 Market Street", orderDTO.getBillingStreetAddress());
        assertEquals("San Fran", orderDTO.getBillingCity());
        assertEquals("1234 West Townsend", orderDTO.getShippingStreetAddress());
        assertEquals("Boston", orderDTO.getShippingCity());
        assertEquals("socks", orderDTO.getProducts().get(0).getName());
        assertEquals("shoes", orderDTO.getProducts().get(1).getName());
        //assertEquals("US", orderDTO.getBillingAlphaCode2());
        //assertEquals("US", orderDTO.getShippingAlphaCode2());
    }

    @Test
    public void map_with_partial_order() {
        Order order = OrderFactory.buildPartialOrder();
        OrderDTO orderDTO = testedOrderMapper().map(order);
        assertEquals("John Doe", orderDTO.getCustomerName());
        assertEquals("93 Newcastle Dr.", orderDTO.getBillingStreetAddress());
        assertTrue(orderDTO.getProducts().isEmpty());
    }

    @Test
    public void map_with_empty_order() {
        Order order = new Order();
        OrderDTO orderDTO = testedOrderMapper().map(order);
        assertNotNull(orderDTO);
    }

    protected abstract OrderMapper testedOrderMapper();
}
