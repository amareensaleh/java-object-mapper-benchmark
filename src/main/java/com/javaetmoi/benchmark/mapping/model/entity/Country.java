package com.javaetmoi.benchmark.mapping.model.entity;

import optional4j.spec.Optional;
import optional4j.spec.Present;

public class Country implements Present<Country> {

    private IsoCode isoCode;

    public Optional<IsoCode> getIsoCode() {
        return Optional.ofNullable(isoCode);
    }

    public void setIsoCode(IsoCode isoCode) {
        this.isoCode = isoCode;
    }
}
