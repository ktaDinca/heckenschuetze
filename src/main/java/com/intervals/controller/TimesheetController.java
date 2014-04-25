package com.intervals.controller;

import com.intervals.model.*;
import com.intervals.service.ActivityService;
import com.intervals.service.DepartmentService;
import com.intervals.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 20/Mar/2014
 */
@Controller
public class TimesheetController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ActivityService activityService;


//    @RequestMapping("/homepage")
//    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
//        return new ModelAndView("homepage-tile");
//    }

    @RequestMapping("/intervals/today")
    public ModelAndView today(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Employee emp = (Employee) session.getAttribute("loggedInUser");

        ModelAndView mv = new ModelAndView("today-tile");

        List<Project> projects = projectService.loadAll();

        mv.addObject("projects", projects);
        mv.addObject("loggedInUser", emp);
        return mv;
    }

    @RequestMapping(value = "/intervals/employee/activities")
    @ResponseBody
    public Map<String, Object> getAllActivities(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        List<Activity> activities = activityService.findAllByUser(emp);

        map.put("message", "succcess");
        map.put("events", activities);

        return map;
    }


    @RequestMapping(value = "/intervals/employee/activities/edit")
    @ResponseBody
    public Map<String, Object> saveActivity(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String description = request.getParameter("description");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String id = request.getParameter("id");
        String projId = request.getParameter("projectId");

        Long projectId = null;
        Long activityId = null;
        Activity activity = null;
        if (id != null && id.length() > 0 && !"undefined".equals(id)) {
            activityId = Long.parseLong(id);
            activity = activityService.findActivityById(activityId);
        }
        else {
            activity = new Activity();
        }

        if (activity != null) {
            Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");

            if (emp != null) {
                activity.setOwner(emp);
            }

            if (start != null && !"undefined".equals(start)) {
                Date s = new Date(Long.parseLong(start));
                activity.setStart(s);
            }
            if (end != null && !"undefined".equals(end)) {
                Date e = new Date(Long.parseLong(end));
                activity.setEnd(e);
            }
            if (description != null && !"undefined".equals(description)) {
                activity.setDescription(description);
            }
            if (projId != null && !"undefined".equals(projId)) {
                projectId = Long.parseLong(projId);
                activity.setProject(projectService.findById(projectId));
            }
            activityService.save(activity);
        }

        map.put("message", "success");
        return map;
    }

    @RequestMapping(value = "/intervals/employee/activities/remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeActivity(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String id = request.getParameter("id");
        Long actId = null;
        if (id != null && id.length() > 0 && !"undefined".equals(id)) {
            actId = Long.parseLong(id);

            activityService.removeById(actId);
        }

        map.put("message", "success");
        return map;
    }

    @RequestMapping("/intervals/homepage-admin")
    public ModelAndView indexAdmin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("homepage-admin-tile");
        List<String> jobs = JobType.getAllValues();

        List<Department> departments = departmentService.loadAll();

        mv.addObject("allJobs", jobs);
        mv.addObject("allDeps", departments);

        return mv;
    }


}
