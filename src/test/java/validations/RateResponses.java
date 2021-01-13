package validations;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public enum RateResponses {
    GBP("GBP",(float)0.8944),
    HKD("HKD",(float)9.4314),
    IDR("IDR",(float)17201.73),
    ILS("ILS",(float)3.8441),
    DKK("DKK",(float)7.4385),
    INR("INR",(float)89.0915),
    CHF("CHF",(float)1.0812),
    MXN("MNX",(float)24.301),
    CZK("CZK",(float)26.19),
    SGD("SGD",(float)1.6146),
    THB("THB",(float)36.641),
    HRK("HRK",(float)7.5805),
    MYR("MYR",(float)4.9343),
    NOK("NOK",(float)10.3765),
    CNY("CNY",(float)7.8576),
    BGN("BGN",(float)1.9558),
    PHP("PHP",(float)58.434),
    SEK("SEK",(float)10.0878),
    PLN("PLN",(float)4.5248),
    ZAR("ZAR",(float)18.7269),
    CAD("CAD",(float)1.551),
    ISK("ISK",(float)156.2),
    BRL("BRL",(float)6.6592),
    RON("RON",(float)4.8713),
    NZD("NZD",(float)1.694),
    TRY("TRY",(float)9.105),
    JPY("JPY",(float)126.74),
    RUB("RUB",(float)90.0537),
    KRW("KRW",(float)1336.38),
    USD("USD",(float)1.2161),
    HUF("HUF",(float)359.66),
    AUD("AUD",(float)1.5742);

    @Getter
    private String ratesCurrency;
    private float ratesValues;
}
