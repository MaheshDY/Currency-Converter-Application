package com.example.CurrencyConverterApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import com.example.CurrencyConverterApp.model.CurrencyConversionRequest;
import com.example.CurrencyConverterApp.model.CurrencyConversionResponse;
import com.example.CurrencyConverterApp.model.ExchangeRateResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrencyConverterService {


  @Value("${exchange.api.baseurl}")
  private String exchangeApiBaseUrl;

  @Value("${exchange.api.access-key}")
  private String exchangeApiAccessKey;
  private final String BASE = "&base=";

  public CurrencyConversionResponse convertCurrency(CurrencyConversionRequest request) throws IOException {
    log.debug("Calling the exchange API");
    double conversionRate = getExchangeRates(request.getFrom(),request.getTo());
    log.debug("Sending the converted amount");
    CurrencyConversionResponse response = new CurrencyConversionResponse();
    response.setConvertedAmount(request.getAmount()*conversionRate);
    return response;
  }

  private double getExchangeRates(String fromCurrency, String toCurrency) throws IOException {
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    Request request = new Request.Builder()
        .url(exchangeApiBaseUrl + toCurrency + BASE + fromCurrency)
        .addHeader("apikey", exchangeApiAccessKey)
        .get()
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        ExchangeRateResponse rateResponse = objectMapper.readValue(responseBody, ExchangeRateResponse.class);
        return rateResponse.getRates().get(toCurrency);
      } else {
        throw new IOException("Unexpected response code: " + response.code());
      }
    }catch (IOException e) {
      // Handle exception or rethrow it as needed
      e.printStackTrace();
      throw e;
    }
  }

}
