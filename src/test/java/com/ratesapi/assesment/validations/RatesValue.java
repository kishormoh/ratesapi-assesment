package com.ratesapi.assesment.validations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RatesValue {
    EUR("EUR"),
    GBP("GBP"),
    HKD("HKD"),
    IDR("IDR"),
    ILS("ILS"),
    DKK("DKK"),
    INR("INR"),
    CHF("CHF"),
    MXN("MNX"),
    CZK("CZK"),
    SGD("SGD"),
    THB("THB"),
    HRK("HRK"),
    MYR("MYR"),
    NOK("NOK"),
    CNY("CNY"),
    BGN("BGN"),
    PHP("PHP"),
    SEK("SEK"),
    PLN("PLN"),
    ZAR("ZAR"),
    CAD("CAD"),
    ISK("ISK"),
    BRL("BRL"),
    RON("RON"),
    NZD("NZD"),
    TRY("TRY"),
    JPY("JPY"),
    RUB("RUB"),
    KRW("KRW"),
    USD("USD"),
    HUF("HUF"),
    AUD("AUD");

    @Getter
    private String baseName;
}
