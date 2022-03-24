package com.javaetmoi.benchmark.mapping.mapper.orika;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import optional4j.spec.Optional;

/**
 * Using custom BoundMapperFacade with no object graph cycles.
 *
 * @see <a href="http://orika-mapper.github.io/orika-docs/performance-tuning.html">http://orika-mapper.github.io/orika-docs/performance-tuning.html</a>
 */
public class OrikaMapper implements OrderMapper {

    private static final Customer CUSTOMER = new Customer();
    private static final Address ADDRESS = new Address();
    private static final AlphaCode2 ALPHA_CODE_2 = new AlphaCode2();

    private BoundMapperFacade<Order, OrderDTO> orderMapper;

    public OrikaMapper() {
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        factory.registerClassMap(factory.classMap(Order.class, OrderDTO.class)
                .field("products", "products")
                .customize(new CustomMapper<Order, OrderDTO>() {
                    @Override
                    public void mapAtoB(Order order, OrderDTO orderDTO, MappingContext context) {

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

                    }
                })
                .toClassMap());
        orderMapper = factory.getMapperFacade(Order.class, OrderDTO.class, false);
    }

    @Override
    public OrderDTO map(Order source) {
        return orderMapper.map(source);
    }
};

