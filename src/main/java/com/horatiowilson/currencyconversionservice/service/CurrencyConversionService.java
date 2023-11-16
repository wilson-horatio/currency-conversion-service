package com.horatiowilson.currencyconversionservice.service;

import com.horatiowilson.currencyconversionservice.dto.CurrencyConversionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class CurrencyConversionService {
    public CurrencyConversionDto calculateCurrencyConversion(CurrencyConversionDto currencyConversionDto) {

        HashMap<String, String> uriVariables = new HashMap<>();

        uriVariables.put("from", currencyConversionDto.getFrom());
        uriVariables.put("to", currencyConversionDto.getTo());
        // Calculate the currency conversion
        ResponseEntity<CurrencyConversionDto> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionDto.class, uriVariables);
        CurrencyConversionDto currencyConversionDto1 = responseEntity.getBody();
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
