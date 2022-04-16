package com.javaetmoi.benchmark.mapping.mapper.manual;

import static com.javaetmoi.benchmark.mapping.mapper.CustomMapper.CUSTOM_MAPPER;
import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import java.util.ArrayList;
import java.util.List;


public final class ManualMapper implements OrderMapper {

    @Override
    public OrderDTO map(Order order) {

        OrderDTO orderDTO = new OrderDTO();

        CUSTOM_MAPPER.map(order, orderDTO);

        if (order.getProducts() != null) {
            List<ProductDTO> targetProducts = new ArrayList<>(order.getProducts().size());
            for (Product product : order.getProducts()) {
                targetProducts.add(new ProductDTO(product.getName()));
            }
            orderDTO.setProducts(targetProducts);
        }
        return orderDTO;
    }

}
