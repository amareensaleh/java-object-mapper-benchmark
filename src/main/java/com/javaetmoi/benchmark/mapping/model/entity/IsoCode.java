package com.javaetmoi.benchmark.mapping.model.entity;

import java.util.Optional;

public class IsoCode {

    private AlphaCode2 alphaCode2;

    public Optional<AlphaCode2> getAlphaCode2() {
        return Optional.ofNullable(alphaCode2);
    }

    public void setAlphaCode2(AlphaCode2 alphaCode2) {
        this.alphaCode2 = alphaCode2;
    }
}
