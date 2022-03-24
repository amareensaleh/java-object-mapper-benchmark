package com.javaetmoi.benchmark.mapping.mapper.selma;

import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.dto.ProductDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.Product;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import optional4j.spec.Optional;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL, withCustom = SelmaOrderMapper.OrderMappingInterceptor.class)
public interface SelmaOrderMapper {

    Customer CUSTOMER = new Customer();
    Address ADDRESS = new Address();
    AlphaCode2 ALPHA_CODE_2 = new AlphaCode2();

    OrderDTO map(Order source);

    ProductDTO map(Product source);

    class OrderMappingInterceptor {

        public void interceptMap(Order source, OrderDTO dest) {

            Optional<Customer> customer = source.getCustomer();
            dest.setCustomerName(customer.orElse(CUSTOMER).getName());

            Optional<Address> optionalBillingAddress = customer.flatMap(Customer::getBillingAddress);
            dest.setBillingAlphaCode2(unwrapAlphaCode2(optionalBillingAddress));

            Address billingAddress = optionalBillingAddress.orElse(ADDRESS);
            dest.setBillingCity(billingAddress.getCity());
            dest.setBillingStreetAddress(billingAddress.getStreet());

            Optional<Address> optionalShippingAddress = customer.flatMap(Customer::getShippingAddress);
            dest.setShippingAlphaCode2(unwrapAlphaCode2(optionalShippingAddress));

            Address shippingAddress = optionalShippingAddress.orElse(ADDRESS);
            dest.setShippingCity(shippingAddress.getCity());
            dest.setShippingStreetAddress(shippingAddress.getStreet());
        }

        private String unwrapAlphaCode2(Optional<Address> address) {
            return address.flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).orElse(ALPHA_CODE_2).getCode();
        }
    }
}
