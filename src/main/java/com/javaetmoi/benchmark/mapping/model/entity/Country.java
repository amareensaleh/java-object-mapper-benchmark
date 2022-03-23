package com.javaetmoi.benchmark.mapping.model.entity;

import java.util.Optional;

public class Country {

    private IsoCode isoCode;

    public Optional<IsoCode> getIsoCode() {
        return Optional.ofNullable(isoCode);
    }

    public void setIsoCode(IsoCode isoCode) {
        this.isoCode = isoCode;
    }
}
