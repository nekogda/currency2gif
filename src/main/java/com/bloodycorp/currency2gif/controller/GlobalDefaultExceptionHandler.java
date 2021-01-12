package com.bloodycorp.currency2gif.controller;

import com.bloodycorp.currency2gif.model.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(Exception e) {
        log.error("default exception handler: ", e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorInfo", new ErrorDto("Oops..."));
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
