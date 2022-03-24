package com.javaetmoi.benchmark.mapping.mapper.selma;

import static com.javaetmoi.benchmark.mapping.mapper.CustomMapper.CUSTOM_MAPPER;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL, withCustom = SelmaOrderMapper.OrderMappingInterceptor.class)
public interface SelmaOrderMapper {

    OrderDTO map(Order source);

    ProductDTO map(Product source);

    class OrderMappingInterceptor {

        public void interceptMap(Order source, OrderDTO dest) {
            CUSTOM_MAPPER.map(source, dest);
        }
    }
}
