package com.javaetmoi.benchmark.mapping.mapper.manual;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import java.util.ArrayList;
import java.util.List;


public final class ManualMapper implements OrderMapper {

    @Override
    public OrderDTO map(Order order) {
        OrderDTO target = new OrderDTO();

        order.getCustomer()
                .ifPresent(customer -> target.setCustomerName(customer.getName()));

        order.getCustomer()
                .flatMap(Customer::getBillingAddress)
                .ifPresent(address -> {
                    target.setBillingCity(address.getCity());
                    target.setBillingStreetAddress(address.getStreet());
                });

        order.getCustomer()
                .flatMap(Customer::getShippingAddress)
                .ifPresent(address -> {
                    target.setShippingCity(address.getCity());
                    target.setShippingStreetAddress(address.getStreet());
                });

        if (order.getProducts() != null) {
            List<ProductDTO> targetProducts = new ArrayList<ProductDTO>(order.getProducts().size());
            for (Product product : order.getProducts()) {
                targetProducts.add(new ProductDTO(product.getName()));
            }
            target.setProducts(targetProducts);
        }
        return target;
    }

}
