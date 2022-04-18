package com.javaetmoi.benchmark;

import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.OrderFactory;
import java.util.Collection;
import optional4j.spec.Optional;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class MapperBenchmark {

    private final Order order = OrderFactory.buildOrder();

    @Setup
    public void setup() {
        for (int i = 0; i < 1000; i++) {
            Optional.ofNullable(null);
        }
    }

    @Benchmark
    public String toCustomerName() {
        return order.getCustomer().map(Customer::getName).orElse(null);
    }

    @Benchmark
    public String toShippingStreetAddress() {
        return order.getCustomer().flatMap(Customer::getShippingAddress).map(Address::getStreet).orElse(null);
    }

    @Benchmark
    public String toShippingCity() {
        return order.getCustomer().flatMap(Customer::getShippingAddress).map(Address::getCity).orElse(null);
    }

    @Benchmark
    public String toShippingAlphaCode2() {
        return order.getCustomer().flatMap(Customer::getShippingAddress).flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).map(AlphaCode2::getCode).orElse(null);
    }

    @Benchmark
    public String toBillingStreetAddress() {
        return order.getCustomer().flatMap(Customer::getBillingAddress).map(Address::getStreet).orElse(null);
    }

    @Benchmark
    public String toBillingCity() {
        return order.getCustomer().flatMap(Customer::getBillingAddress).map(Address::getCity).orElse(null);
    }

    @Benchmark
    public String toBillingAlphaCode2() {
        return order.getCustomer().flatMap(Customer::getBillingAddress).flatMap(Address::getCountry).flatMap(Country::getIsoCode).flatMap(IsoCode::getAlphaCode2).map(AlphaCode2::getCode).orElse(null);
    }

    public static void main(String... args) throws Exception {
        Options opts = new OptionsBuilder().include(MapperBenchmark.class.getSimpleName()).warmupIterations(5).measurementIterations(5).jvmArgs("-server").forks(1).resultFormat(ResultFormatType.TEXT).build();

        Collection<RunResult> results = new Runner(opts).run();
        for (RunResult result : results) {
            Result<?> r = result.getPrimaryResult();
            System.out.println("API replied benchmark score: " + r.getScore() + " " + r.getScoreUnit() + " over " + r.getStatistics().getN() + " iterations");
        }
    }
}
