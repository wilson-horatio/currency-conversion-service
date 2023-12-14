package com.horatiowilson.currencyconversionservice.controller;

import com.horatiowilson.currencyconversionservice.dto.CurrencyConversionDto;
import com.horatiowilson.currencyconversionservice.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    @Autowired
    CurrencyConversionService currencyConversionService;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionDto calculateCurrencyConversion(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversionDto currencyConversionDto = new CurrencyConversionDto();
        currencyConversionDto.setFrom(from);
        currencyConversionDto.setTo(to);
        currencyConversionDto.setQuantity(quantity);
        return currencyConversionService.calculateCurrencyConversion(currencyConversionDto);
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionDto calculateCurrencyConversionFeign(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyConversionDto currencyConversionDto = new CurrencyConversionDto();
        currencyConversionDto.setFrom(from);
        currencyConversionDto.setTo(to);
        currencyConversionDto.setQuantity(quantity);
        return currencyConversionService.calculateCurrencyConversionFeign(currencyConversionDto);
    }
}
