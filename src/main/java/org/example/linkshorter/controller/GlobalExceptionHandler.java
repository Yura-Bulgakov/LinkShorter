package org.example.linkshorter.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandling(HttpServletRequest request, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", request.getAttribute("javax.servlet.error.status_code"));
        mav.addObject("path", request.getAttribute("javax.servlet.error.request_uri"));
        mav.addObject("message", e.getMessage());
        mav.setViewName("error");
        return mav;
    }

}
