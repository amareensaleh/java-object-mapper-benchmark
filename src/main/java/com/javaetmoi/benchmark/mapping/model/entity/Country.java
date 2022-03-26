package com.javaetmoi.benchmark.mapping.model.entity;

import optional4j.spec.Absent;
import optional4j.spec.Optional;
import optional4j.spec.Present;

public final class Country implements Present<Country> {

    private IsoCode isoCode;

    public Optional<IsoCode> getIsoCode() {
        return isoCode != null ? isoCode : Absent.nothing();
    }

    public void setIsoCode(IsoCode isoCode) {
        this.isoCode = isoCode;
    }
}
