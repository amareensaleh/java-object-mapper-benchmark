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


public final class ManualMapper implements OrderMapper {

    @Override
    public OrderDTO map(Order source) {
        OrderDTO target = new OrderDTO();
        Customer customer = source.getCustomer();
        if (customer != null) {
            target.setCustomerName(customer.getName());
            Address billingAddress = customer.getBillingAddress();
            if (billingAddress != null) {
                target.setBillingCity(billingAddress.getCity());
                target.setBillingStreetAddress(billingAddress.getStreet());
                target.setBillingAlphaCode2(getAlphaCode2(billingAddress));
            }
            Address shippingAddress = customer.getShippingAddress();
            if (shippingAddress != null) {
                target.setShippingCity(shippingAddress.getCity());
                target.setShippingStreetAddress(shippingAddress.getStreet());
                target.setShippingAlphaCode2(getAlphaCode2(shippingAddress));
            }
        }
        if (source.getProducts() != null) {
            List<ProductDTO> targetProducts = new ArrayList<>(source.getProducts().size());
            for (Product product : source.getProducts()) {
                targetProducts.add(new ProductDTO(product.getName()));
            }
            target.setProducts(targetProducts);
        }
        return target;
    }

    private String getAlphaCode2(Address address) {
        Country country = address.getCountry();
        if (country != null) {
            IsoCode isoCode = country.getIsoCode();
            if (isoCode != null) {
                AlphaCode2 alphaCode2 = isoCode.getAlphaCode2();
                if (alphaCode2 != null) {
                    return alphaCode2.getCode();
                }
            }
        }
        return null;
    }

}
