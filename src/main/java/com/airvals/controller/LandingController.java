package com.airvals.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 25/May/2014
 */

@Controller
public class LandingController {

    @RequestMapping("/airvals")
    public ModelAndView index (HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("landing-tile");
        return mv;
    }

}
