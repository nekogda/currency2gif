package com.bloodycorp.currency2gif.controller;

import com.bloodycorp.currency2gif.config.AppConfig;
import com.bloodycorp.currency2gif.model.ErrorDto;
import com.bloodycorp.currency2gif.model.RateReportDto;
import com.bloodycorp.currency2gif.services.Currency2GifService;
import com.bloodycorp.currency2gif.services.Currency2GifServiceResponse;
import com.bloodycorp.currency2gif.services.exceptions.Currency2GifBadRequestException;
import com.bloodycorp.currency2gif.services.exceptions.Currency2GifUnauthorizedException;
import com.bloodycorp.currency2gif.services.exceptions.Currency2GifUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Currency2GifController {
    private static final Logger log = LoggerFactory.getLogger(Currency2GifController.class);

    private final Currency2GifService currency2GifService;
    private final String currencyCode;

    public Currency2GifController(Currency2GifService currency2GifService, AppConfig cfg) {
        this.currency2GifService = currency2GifService;
        this.currencyCode = cfg.getExchangeRateServiceCurrencyCode();
    }

    @GetMapping("/")
    public String root(Model model) {
        Currency2GifServiceResponse response = currency2GifService.getExchangeInfo(currencyCode);
        var dto = new RateReportDto(response.getUrl(), response.getCurrentRate());
        model.addAttribute("exchangeInfo", dto);
        return "rateReport";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Currency2GifBadRequestException.class})
    public ModelAndView badRequestHandler(RuntimeException e) {
        return handleException(e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({Currency2GifUnauthorizedException.class})
    public ModelAndView UnauthorizedHandler(RuntimeException e) {
        return handleException(e);
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler({Currency2GifUnavailableException.class})
    public ModelAndView UnavailableHandler(RuntimeException e) {
        return handleException(e);
    }

    private ModelAndView handleException(RuntimeException e) {
        log.error("Controller start exception handling: ", e);

        ModelAndView mav = new ModelAndView();
        mav.addObject("errorInfo", new ErrorDto(e.getMessage()));
        mav.setViewName("error");

        return mav;
    }
}
