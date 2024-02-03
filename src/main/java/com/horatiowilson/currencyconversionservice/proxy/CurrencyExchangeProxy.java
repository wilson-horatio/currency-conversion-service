package com.horatiowilson.currencyconversionservice.proxy;

import com.horatiowilson.currencyconversionservice.dto.CurrencyConversionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionDto retrieveExchangeValue(
      @PathVariable String from,
      @PathVariable String to
    );
}
