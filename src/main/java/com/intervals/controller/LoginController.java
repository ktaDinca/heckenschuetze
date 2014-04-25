package com.intervals.controller;

import com.intervals.model.Employee;
import com.intervals.model.JobType;
import com.intervals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/intervals")
    public ModelAndView index() {
        return new ModelAndView("login-tile");
    }

    @RequestMapping(value = "/intervals/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView();
        Employee emp = employeeService.findEmployeeByUsername(username);

        if (emp == null) {
            mv.setViewName("redirect:/intervals");
            return mv;
        }
        else {
            mv.setViewName("redirect:/intervals/today");
            saveUserOnSession(request, emp);
         }
        return mv;
    }

    private void saveUserOnSession(HttpServletRequest request, Employee emp) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedInUser") == null) {
            session.setAttribute("loggedInUser", emp);
        }
    }

}