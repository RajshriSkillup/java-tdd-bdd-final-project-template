package com.productstore.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:/index.html");
        return modelAndView;
    }
    
    @GetMapping("/health")
    public ModelAndView health() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:/health.html");
        return modelAndView;
    }
}