package com.example.CurrencyConverterApp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CurrencyConversionRequest {
  private double amount;
  private String from;
  private String to;
}
