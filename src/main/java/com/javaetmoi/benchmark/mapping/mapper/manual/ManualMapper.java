package com.javaetmoi.benchmark.mapping.mapper.manual;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import java.util.ArrayList;
import java.util.List;
import optional4j.spec.Optional;


public final class ManualMapper implements OrderMapper {

    private static final Customer CUSTOMER = new Customer();
    private static final Address ADDRESS = new Address();
    private static final AlphaCode2 ALPHA_CODE_2 = new AlphaCode2();

    @Override
    public OrderDTO map(Order order) {

        OrderDTO orderDTO = new OrderDTO();
        Optional<Customer> customer = order.getCustomer();
        orderDTO.setCustomerName(customer.orElse(CUSTOMER).getName());

        Optional<Address> optBillingAddress = customer.flatMap(Customer::getBillingAddress);
        orderDTO.setBillingAlphaCode2(optBillingAddress.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).orElse(ALPHA_CODE_2).getCode());

        Address billingAddress = optBillingAddress.orElse(ADDRESS);
        orderDTO.setBillingCity(billingAddress.getCity());
        orderDTO.setBillingStreetAddress(billingAddress.getStreet());

        Optional<Address> optShippingAddress = customer.flatMap(Customer::getShippingAddress);
        orderDTO.setShippingAlphaCode2(optShippingAddress.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).orElse(ALPHA_CODE_2).getCode());

        Address shippingAddress = optShippingAddress.orElse(ADDRESS);
        orderDTO.setShippingCity(shippingAddress.getCity());
        orderDTO.setShippingStreetAddress(shippingAddress.getStreet());

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
