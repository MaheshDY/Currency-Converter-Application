package com.example.CurrencyConverterApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {


  @JsonProperty("success")
  private boolean success;

  @JsonProperty("timestamp")
  private long timestamp;

  @JsonProperty("base")
  private String base;

  @JsonProperty("date")
  private String date;

  @JsonProperty("rates")
  private Map<String, Double> rates;
}
