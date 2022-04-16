package com.javaetmoi.benchmark;

import com.javaetmoi.benchmark.mapping.mapper.OrderMapper;
import com.javaetmoi.benchmark.mapping.mapper.bull.BullMapper;
import com.javaetmoi.benchmark.mapping.mapper.datus.DatusMapper;
import com.javaetmoi.benchmark.mapping.mapper.jmapper.JMapperMapper;
import com.javaetmoi.benchmark.mapping.mapper.manual.ManualMapper;
import com.javaetmoi.benchmark.mapping.mapper.mapstruct.MapStructMapper;
import com.javaetmoi.benchmark.mapping.mapper.modelmapper.ModelMapper;
import com.javaetmoi.benchmark.mapping.mapper.orika.OrikaMapper;
import com.javaetmoi.benchmark.mapping.mapper.remappe.ReMappeMapper;
import com.javaetmoi.benchmark.mapping.mapper.selma.SelmaMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.OrderFactory;
import java.util.Collection;
import java.util.Optional;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
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

    @Param({"Manual", "MapStruct", "Selma", "JMapper", "datus", "Orika", "ModelMapper", "BULL", "Dozer", "ReMap"})
    private String type;

    private OrderMapper mapper;

    private final Order order = OrderFactory.buildOrder();

    @Setup(Level.Trial)
    public void setup() {
        switch (type) {
            case "Dozer":
//                mapper = new DozerMapper();
                break;
            case "Orika":
                mapper = new OrikaMapper();
                break;
            case "ModelMapper":
                mapper = new ModelMapper();
                break;
            case "MapStruct":
                mapper = new MapStructMapper();
                break;
            case "Selma":
                mapper = new SelmaMapper();
                break;
            case "JMapper":
               mapper = new JMapperMapper();
                break;
            case "Manual":
                mapper = new ManualMapper();
                break;
            case "BULL":
                mapper = new BullMapper();
                break;
            case "datus":
                mapper = new DatusMapper();
                break;
            case "ReMap":
                mapper = new ReMappeMapper();
                break;
            default:
                throw new IllegalStateException("Unknown type: " + type);
        }

        for (int i = 0; i < 1000; i++) {
            Optional.ofNullable(null);
        }

        mapper.map(order);
    }

    @Benchmark
    public OrderDTO mapper() {
        return mapper.map(order);
    }

    public static void main(String... args) throws Exception {
        Options opts = new OptionsBuilder()
                .include(MapperBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .jvmArgs("-server")
                .forks(1)
                .resultFormat(ResultFormatType.TEXT)
                .build();

        Collection<RunResult> results = new Runner(opts).run();
        for (RunResult result : results) {
            Result<?> r = result.getPrimaryResult();
            System.out.println("API replied benchmark score: "
                    + r.getScore() + " "
                    + r.getScoreUnit() + " over "
                    + r.getStatistics().getN() + " iterations");
        }
    }
}
