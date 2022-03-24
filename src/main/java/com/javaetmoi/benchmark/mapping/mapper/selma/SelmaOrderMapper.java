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
import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import optional4j.spec.Optional;

@Mapper(
        withIgnoreMissing = IgnoreMissing.ALL,
        withIgnoreFields = "customer",
        withCustomFields = {
                @Field(value = {"customer", "customerName"}, withCustom = SelmaOrderMapper.UnwrapCustomerName.class),
                @Field(value = {"customer", "billingStreetAddress"}, withCustom = SelmaOrderMapper.UnwrapBillingCity.class),
                @Field(value = {"customer", "billingCity"}, withCustom = SelmaOrderMapper.UnwrapBillingStreet.class),
                @Field(value = {"customer", "billingAlphacode2"}, withCustom = SelmaOrderMapper.UnwrapBillingAlphaCode2.class),
                @Field(value = {"customer", "shippingAlphacode2"}, withCustom = SelmaOrderMapper.UnwrapShippingCity.class),
                @Field(value = {"customer", "billingCity"}, withCustom = SelmaOrderMapper.UnwrapShippingStreet.class),
                @Field(value = {"customer", "shippingStreetAddress"}, withCustom = SelmaOrderMapper.UnwrapShippingAlphaCode2.class)
        }
)
public interface SelmaOrderMapper {

    Customer CUSTOMER = new Customer();
    Address ADDRESS = new Address();
    AlphaCode2 ALPHA_CODE_2 = new AlphaCode2();

    OrderDTO map(Order source);

    ProductDTO map(Product source);

    class UnwrapBillingStreet {
        public String unwrapBillingStreet(Optional<Customer> customer) {
            return customer.flatMap(Customer::getBillingAddress).orElse(ADDRESS).getStreet();
        }
    }

    class UnwrapShippingStreet {
        public String unwrapShippingStreet(Optional<Customer> customer) {
            return customer.flatMap(Customer::getShippingAddress).orElse(ADDRESS).getStreet();
        }
    }

    class UnwrapShippingAlphaCode2 {
        public String unwrapShippingAlphaCode2(Optional<Customer> customer) {
            return customer.flatMap(Customer::getShippingAddress)
                    .flatMap(Address::getCountry)
                    .flatMap(Country::getIsoCode)
                    .flatMap(IsoCode::getAlphaCode2)
                    .orElse(ALPHA_CODE_2)
                    .getCode();
        }
    }

    class UnwrapBillingAlphaCode2 {
        public String unwrapBillingAlphaCode2(Optional<Customer> customer) {
            return customer.flatMap(Customer::getBillingAddress)
                    .flatMap(Address::getCountry)
                    .flatMap(Country::getIsoCode)
                    .flatMap(IsoCode::getAlphaCode2)
                    .orElse(ALPHA_CODE_2)
                    .getCode();
        }
    }

    class UnwrapBillingCity {
        public String unwrapBillingCity(Optional<Customer> customer) {
            return customer.flatMap(Customer::getBillingAddress).orElse(ADDRESS).getCity();
        }
    }

    class UnwrapShippingCity {
        public String unwrapShippingCity(Optional<Customer> customer) {
            return customer.flatMap(Customer::getShippingAddress).orElse(ADDRESS).getCity();
        }
    }

    class UnwrapCustomerName {
        public String unwrapCustomerName(Optional<Customer> customer) {
            return customer.orElse(CUSTOMER).getName();
        }
    }
}
