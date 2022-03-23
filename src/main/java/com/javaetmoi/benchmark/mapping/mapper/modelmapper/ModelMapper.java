package com.javaetmoi.benchmark.mapping.mapper.modelmapper;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import optional4j.spec.Optional;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class ModelMapper implements OrderMapper {

    private static final Customer CUSTOMER = new Customer();
    private static final Address ADDRESS = new Address();
    private static final AlphaCode2 ALPHA_CODE_2 = new AlphaCode2();

    private static final Converter<Order, OrderDTO> POST_CONVERTER = mappingContext -> {

        OrderDTO orderDTO = mappingContext.getDestination();

        Order order = mappingContext.getSource();

        orderDTO.setCustomerName(order.getCustomer().orElse(CUSTOMER).getName());

        Optional<Address> optBillingAddress = order.getCustomer().flatMap(Customer::getBillingAddress);
        Address billingAddress = optBillingAddress.orElse(ADDRESS);
        orderDTO.setBillingCity(billingAddress.getCity());
        orderDTO.setBillingStreetAddress(billingAddress.getStreet());
        orderDTO.setBillingAlphaCode2(optBillingAddress.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).orElse(ALPHA_CODE_2).getCode());

        Optional<Address> optShippingAddress = order.getCustomer().flatMap(Customer::getShippingAddress);
        Address shippingAddress = optShippingAddress.orElse(ADDRESS);
        orderDTO.setShippingCity(shippingAddress.getCity());
        orderDTO.setShippingStreetAddress(shippingAddress.getStreet());
        orderDTO.setShippingAlphaCode2(optShippingAddress.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).orElse(ALPHA_CODE_2).getCode());

        return orderDTO;
    };

    private org.modelmapper.ModelMapper modelMapper;

    public ModelMapper() {
        modelMapper = new org.modelmapper.ModelMapper();
        modelMapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
            }
        }).setPostConverter(POST_CONVERTER);
    }

    @Override
    public OrderDTO map(Order source) {
        return modelMapper.map(source, OrderDTO.class);
    }
}
