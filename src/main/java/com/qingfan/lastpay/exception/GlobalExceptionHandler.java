package com.qingfan.lastpay.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        log.error("❌ Unexpected error occurred: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "Something went wrong. Please try again later.");
        return "error";
    }

}
