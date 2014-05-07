package com.intervals.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 07/May/2014
 */
@Controller
public class LogoutController {

    /**
     * Logs out current user by invalidating the session.
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/intervals/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        ModelAndView mv = new ModelAndView("redirect:/intervals");

        return mv;
    }
}
