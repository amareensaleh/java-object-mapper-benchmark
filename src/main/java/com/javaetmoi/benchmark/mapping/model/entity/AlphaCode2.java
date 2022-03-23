package com.javaetmoi.benchmark.mapping.model.entity;


import optional4j.spec.Present;

public class AlphaCode2 implements Present<AlphaCode2> {

    private String code;

    private int year;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
