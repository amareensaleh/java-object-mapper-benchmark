package com.javaetmoi.benchmark;

import com.javaetmoi.benchmark.mapping.mapper.CustomMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Address;
import com.javaetmoi.benchmark.mapping.model.entity.AlphaCode2;
import com.javaetmoi.benchmark.mapping.model.entity.Country;
import com.javaetmoi.benchmark.mapping.model.entity.Customer;
import com.javaetmoi.benchmark.mapping.model.entity.IsoCode;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.OrderFactory;
import java.util.Collection;
import com.google.common.base.Optional;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class MapperBenchmark {

    private final Order order = OrderFactory.buildOrder();

    private final OrderDTO orderDTO = new OrderDTO();

    @Setup
    public void setup() {
        for (int i = 0; i < 1000; i++) {
            Optional.fromNullable(null);
        }
    }

    @Benchmark
    public void toCustomerName(Blackhole blackhole) {
        blackhole.consume(order.getCustomer().transform(Customer::getName).orNull());
    }

    @Benchmark
    public void toShippingStreetAddress(Blackhole blackhole) {
        blackhole.consume(order.getCustomer().transform(Customer::getShippingAddress).transform(Address::getStreet).orNull());
    }

    @Benchmark
    public void toShippingCity(Blackhole blackhole) {
        blackhole.consume(order.getCustomer().transform(Customer::getShippingAddress).transform(Address::getCity).orNull());
    }

    @Benchmark
    public void toShippingAlphaCode2(Blackhole blackhole) {
        blackhole.consume(order.getCustomer().transform(Customer::getShippingAddress).transform(Address::getCountry).transform(Country::getIsoCode).transform(IsoCode::getAlphaCode2).transform(AlphaCode2::getCode).orNull());
    }

    @Benchmark
    public void toBillingStreetAddress(Blackhole blackhole) {
        blackhole.consume(order.getCustomer().transform(Customer::getBillingAddress).transform(Address::getStreet).orNull());
    }

    @Benchmark
    public void toBillingCity(Blackhole blackhole) {
        blackhole.consume(order.getCustomer().transform(Customer::getBillingAddress).transform(Address::getCity).orNull());
    }

    @Benchmark
    public void toBillingAlphaCode2(Blackhole blackhole) {
        blackhole.consume(order.getCustomer().transform(Customer::getBillingAddress).transform(Address::getCountry).transform(Country::getIsoCode).transform(IsoCode::getAlphaCode2).transform(AlphaCode2::getCode).orNull());
    }

    @Benchmark
    public void combined(Blackhole blackhole) {
        CustomMapper.CUSTOM_MAPPER.map(order, orderDTO);
        blackhole.consume(orderDTO);
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
