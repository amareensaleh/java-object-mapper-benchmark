package com.javaetmoi.benchmark.mapping.model.entity;

import optional4j.spec.Absent;
import optional4j.spec.Optional;
import optional4j.spec.Present;

public final class IsoCode implements Present<IsoCode> {

    private AlphaCode2 alphaCode2;

    public Optional<AlphaCode2> getAlphaCode2() {
        return alphaCode2 != null ? alphaCode2 : Absent.nothing();
    }

    public void setAlphaCode2(AlphaCode2 alphaCode2) {
        this.alphaCode2 = alphaCode2;
    }
}
