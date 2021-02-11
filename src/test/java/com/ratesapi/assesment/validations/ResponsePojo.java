package com.ratesapi.assesment.validations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
public class ResponsePojo {

    private final String base;
    private final String dates;
    private final Map<String,Float> rates;

}
