package com.airvals.controller;

import com.airvals.model.Interaction;
import com.airvals.model.User;
import com.airvals.service.InteractionService;
import com.airvals.service.UserService;
import com.intervals.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 10/Jun/2014
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private InteractionService interactionService;

    @RequestMapping(value = "/airvals/user/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        User user = new User();

        String _email = request.getParameter("email");
        if(StringUtils.isNotEmptyNullOrUndefined(_email)) {
            // TODO: check daca e email (pattern)
            user.setEmail(_email);
        }

        String _firstname = request.getParameter("firstname");
        if(StringUtils.isNotEmptyNullOrUndefined(_firstname)) {
            user.setFirstname(_firstname);
        }

        String _lastname = request.getParameter("lastname");
        if(StringUtils.isNotEmptyNullOrUndefined(_firstname)) {
            user.setLastname(_lastname);
        }

        String _password = request.getParameter("password");
        if(StringUtils.isNotEmptyNullOrUndefined(_password)) {
            user.setPassword(_password);
        }

        user.setIsAdmin(false);
        userService.saveOrUpdate(user);

        map.put("message", "success");
        return map;
    }

    @RequestMapping(value = "/airvals/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logInUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String _email = request.getParameter("email");
        String _password = request.getParameter("password");

        User user = null;
        if(!StringUtils.isNotEmptyNullOrUndefined(_email) ||
                !StringUtils.isNotEmptyNullOrUndefined(_password)) {
            map.put("message", "failed");
            return map;
        }
        user = userService.findUserByEmail(_email);
        if(user == null) {
            map.put("message", "failed");
            map.put("error", "unknown_user");
            return map;
        }

        if (!_password.equals(user.getPassword())) {
            map.put("message", "failed");
            map.put("error", "wrong_password");
            return map;
        }

        request.getSession().setAttribute("loggedAirvalsUser", user);
        map.put("message", "success");
        map.put("loggedInUser", user);
        return map;
    }

    @RequestMapping(value = "/airvals/admin/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView logInAdmin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        String _email = request.getParameter("email");
        String _password = request.getParameter("password");
        if (!StringUtils.isNotEmptyNullOrUndefined(_email) || !StringUtils.isNotEmptyNullOrUndefined(_password)) {
            mv.setViewName("landing-tile");
            return mv;
        }

        User admin = userService.findUserByEmail(_email);
        if (admin == null || !_password.equals(admin.getPassword())) {
            mv.setViewName("landing-tile");
            return mv;
        }

        mv.setViewName("airvals-admin-tile");
        return mv;
    }

    @RequestMapping(value = "/airvals/user/history", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCurrentUserHistory(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        User user = (User) request.getSession().getAttribute("loggedAirvalsUser");
        if (user == null) {
            map.put("message", "failed");
            return map;
        }

        List<Interaction> interactions = interactionService.findLegalInteractionsByUser(user);
        map.put("message", "success");
        map.put("interactions", interactions);

        return map;
    }
}
