package com.horatiowilson.currencyconversionservice.service;

import com.horatiowilson.currencyconversionservice.dto.CurrencyConversionDto;
import com.horatiowilson.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class CurrencyConversionService {

    @Autowired
    CurrencyExchangeProxy proxy;
    @Autowired
    private RestTemplate restTemplate;

    public CurrencyConversionDto calculateCurrencyConversion(CurrencyConversionDto currencyConversionDto) {

        HashMap<String, String> uriVariables = new HashMap<>();

        uriVariables.put("from", currencyConversionDto.getFrom());
        uriVariables.put("to", currencyConversionDto.getTo());
        // Calculate the currency conversion
        // @TODO: change hardcoded localhost to docker/kubernetes service call.
        ResponseEntity<CurrencyConversionDto> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionDto.class, uriVariables);
        CurrencyConversionDto currencyConversionDto1 = responseEntity.getBody();
        return calculateCurrencyConversion(currencyConversionDto, currencyConversionDto1);
    }

    public CurrencyConversionDto calculateCurrencyConversionFeign(CurrencyConversionDto currencyConversionDto) {
        CurrencyConversionDto currencyConversionDto1 = proxy.retrieveExchangeValue(currencyConversionDto.getFrom(), currencyConversionDto.getTo());
        return calculateCurrencyConversion(currencyConversionDto, currencyConversionDto1);
    }

    private CurrencyConversionDto calculateCurrencyConversion(CurrencyConversionDto currencyConversionDto, CurrencyConversionDto currencyConversionDto1) {
        if (currencyConversionDto1 != null) {
            return new CurrencyConversionDto(
                    currencyConversionDto1.getId(),
                    currencyConversionDto1.getFrom(),
                    currencyConversionDto1.getTo(),
                    currencyConversionDto1.getConversionMultiple(),
                    currencyConversionDto.getQuantity(),
                    currencyConversionDto1.getConversionMultiple().multiply(currencyConversionDto.getQuantity()),
                    currencyConversionDto1.getEnvironment());
        }
        else {
            return null;
        }
    }
}
