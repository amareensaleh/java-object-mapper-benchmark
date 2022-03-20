package com.javaetmoi.benchmark.mapping.mapper.bull;

import com.hotels.beans.BeanUtils;
import com.hotels.beans.transformer.BeanTransformer;
import com.hotels.transformer.model.FieldMapping;
import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;

/**
 * BULL mapper.
 * For more details see: https://github.com/HotelsDotCom/bull.
 */
public class BullMapper implements OrderMapper {
    private final BeanTransformer transformer;

    public BullMapper() {
        FieldMapping[] fieldMappings = new FieldMapping[] {
                new FieldMapping("customer.name", "customerName"),
                new FieldMapping("customer.billingAddress.city", "billingCity"),
                new FieldMapping("customer.billingAddress.street", "billingStreetAddress"),
                new FieldMapping("customer.shippingAddress.city", "shippingCity"),
                new FieldMapping("customer.shippingAddress.street", "shippingStreetAddress"),
                new FieldMapping("customer.shippingAddress.country.isoCode.alphaCode2.code", "shippingAlphaCode2"),
                new FieldMapping("customer.billingAddress.country.isoCode.alphaCode2.code", "billingAlphaCode2")
        };
        this.transformer = new BeanUtils().getTransformer().withFieldMapping(fieldMappings);
    }

    @Override
    public OrderDTO map(Order source) {
        return transformer.transform(source, OrderDTO.class);
    }
}