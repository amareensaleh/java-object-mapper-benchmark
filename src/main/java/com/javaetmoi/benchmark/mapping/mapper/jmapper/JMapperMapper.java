package com.javaetmoi.benchmark.mapping.mapper.jmapper;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;

import static com.googlecode.jmapper.api.JMapperAPI.attribute;
import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;

public class JMapperMapper implements OrderMapper {

    JMapper<OrderDTO, Order> mapper;

    public JMapperMapper() {

        JMapperAPI api = new JMapperAPI()
                .add(mappedClass(OrderDTO.class)
                    .add(attribute("shippingStreetAddress").value("${customer.shippingAddress.street}"))
                    .add(attribute("shippingCity").value("${customer.shippingAddress.city}"))
                    .add(attribute("billingStreetAddress").value("${customer.billingAddress.street}"))
                    .add(attribute("shippingAlphaCode2").value("${customer.shippingAddress.country.isoCode.alphaCode2.code}"))
                    .add(attribute("billingAlphaCode2").value("${customer.billingAddress.country.isoCode.alphaCode2.code}"))
                    .add(attribute("billingCity").value("${customer.billingAddress.city}"))
                    .add(attribute("products").value("products"))
                    .add(attribute("customerName").value("${customer.name}")))
                .add(mappedClass(ProductDTO.class)
                    .add(attribute("name").value("name")));

        mapper = new JMapper<OrderDTO, Order>(OrderDTO.class, Order.class, api);
    }

    @Override
    public OrderDTO map(Order source) {
        // Waiting https://github.com/jmapper-framework/jmapper-core/issues/43
        // By waiting: hand mapping with by practice 'cause we are changing the source object.
        if (source.getCustomer() == null) {
            source.setCustomer(new Customer());
        }
        if (source.getCustomer().getShippingAddress() == null) {
            source.getCustomer().setShippingAddress(new Address());
        }
        if (source.getCustomer().getBillingAddress() == null) {
            source.getCustomer().setBillingAddress(new Address());
        }
        if (source.getCustomer().getShippingAddress().getCountry() == null) {
            source.getCustomer().getShippingAddress().setCountry(new Country());
        }
        if (source.getCustomer().getShippingAddress().getCountry().getIsoCode() == null) {
            source.getCustomer().getShippingAddress().getCountry().setIsoCode(new IsoCode());
        }
        if (source.getCustomer().getShippingAddress().getCountry().getIsoCode().getAlphaCode2() == null) {
            source.getCustomer().getShippingAddress().getCountry().getIsoCode().setAlphaCode2(new AlphaCode2());
        }
        if (source.getCustomer().getBillingAddress().getCountry() == null) {
            source.getCustomer().getBillingAddress().setCountry(new Country());
        }
        if (source.getCustomer().getBillingAddress().getCountry().getIsoCode() == null) {
            source.getCustomer().getBillingAddress().getCountry().setIsoCode(new IsoCode());
        }
        if (source.getCustomer().getBillingAddress().getCountry().getIsoCode().getAlphaCode2() == null) {
            source.getCustomer().getBillingAddress().getCountry().getIsoCode().setAlphaCode2(new AlphaCode2());
        }
        return mapper.getDestination(source);
    }

}
