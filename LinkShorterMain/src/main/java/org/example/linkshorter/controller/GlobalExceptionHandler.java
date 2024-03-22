package org.example.linkshorter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandling(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if (e.getClass().isAnnotationPresent(ResponseStatus.class)) {
            response.setStatus(e.getClass().getAnnotation(ResponseStatus.class).value().value());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", response.getStatus());
        mav.addObject("path", request.getRequestURI());
        mav.addObject("message", e.getMessage());
        mav.setViewName("error");
        return mav;
    }

}
