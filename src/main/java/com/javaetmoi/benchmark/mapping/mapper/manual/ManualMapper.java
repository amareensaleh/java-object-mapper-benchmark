package com.javaetmoi.benchmark.mapping.mapper.manual;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import java.util.ArrayList;
import java.util.List;
import optional4j.spec.Optional;


public final class ManualMapper implements OrderMapper {

    @Override
    public OrderDTO map(Order order) {

        OrderDTO orderDTO = new OrderDTO();

        order.getCustomer().ifPresent(aCustomer -> {

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

        if (order.getProducts() != null) {
            List<ProductDTO> targetProducts = new ArrayList<ProductDTO>(order.getProducts().size());
            for (Product product : order.getProducts()) {
                targetProducts.add(new ProductDTO(product.getName()));
            }
            orderDTO.setProducts(targetProducts);
        }
        return orderDTO;
    }
}
