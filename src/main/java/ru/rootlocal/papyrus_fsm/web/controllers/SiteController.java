package ru.rootlocal.papyrus_fsm.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping(value = "/")
public class SiteController {

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("site/index");
        modelAndView.getModel().put("test", String.format("Date: %s", new Date()));
        return modelAndView;
    }
}
