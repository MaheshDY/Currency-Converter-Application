package com.example.CurrencyConverterApp.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import com.example.CurrencyConverterApp.model.CurrencyConversionRequest;
import com.example.CurrencyConverterApp.model.CurrencyConversionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.CurrencyConverterApp.service.CurrencyConverterService;

@Controller
@Slf4j
public class CurrencyConverterController {

  private final CurrencyConverterService service;

  public CurrencyConverterController(CurrencyConverterService service) {
    this.service = service;
  }

  /**
   * @return initial html page
   */
  @GetMapping("/")
  public String showCurrencyConverterPage(HttpServletResponse response) {
    log.debug("Displaying the home page");
    response.setHeader("Content-Type", "text/html");
    return "currencyConverter";
  }

  /**
   * Coverting the amount from base price to target price.
   *
   * @return double converted value.
   */
  @GetMapping("/convert")
  public ResponseEntity<CurrencyConversionResponse> convertCurrency(
      @ModelAttribute CurrencyConversionRequest request) throws IOException {
    log.debug("Converting the amount");
    CurrencyConversionResponse response = service.convertCurrency(request);
    log.debug("Sending the response to user");
    return ResponseEntity.ok(response);
  }
}
