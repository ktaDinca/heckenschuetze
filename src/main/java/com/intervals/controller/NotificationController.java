package com.intervals.controller;

import com.intervals.model.Employee;
import com.intervals.model.JobType;
import com.intervals.model.Notification;
import com.intervals.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 01/May/2014
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/intervals/notifications/check")
    @ResponseBody
    public Map<String, Object> check(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        if (emp == null || JobType.CLERK.equals(emp.getJob())) {
            map.put("message", "failed");
            return map;
        }

        List<Notification> notifications = notificationService.findUnseenNotifications(emp);

        map.put("notifications", notifications);
        map.put("message", "success");
        return map;
    }
}
