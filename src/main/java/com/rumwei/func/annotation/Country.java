package com.rumwei.func.annotation;

public class Country {
    @Action(type = OperationType.INSERT)
    public HuBei huBei;
    @Action(type = OperationType.UPDATE)
    public ShangHai shangHai;
    public Country(HuBei huBei, ShangHai shangHai) {
        this.huBei = huBei;
        this.shangHai = shangHai;
    }
}
