package com.intervals.controller;

import com.intervals.model.*;
import com.intervals.service.*;
import com.intervals.util.DateUtils;
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

    @Autowired
    private WeeklySheetService weeklySheetService;

    @Autowired
    private NotificationService notificationService;

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

    @RequestMapping("/intervals/weekly")
    public ModelAndView weeklyTimesheets(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("weekly-timesheet-tile");
        return mv;
    }

    @RequestMapping("/intervals/weekly/review")
    public ModelAndView reviewTimesheets(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("weekly-review-tile");
        return mv;
    }

    @RequestMapping(value = "/intervals/weekly/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getWeekly(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String _start = request.getParameter("start");
        if (_start == null || "undefined".equals(_start)) {
            map.put("message", "failed");
            return map;
        }

        Date start = new Date(Long.parseLong(_start));
        Date thisWeeksMonday = DateUtils.findThisWeeksMonday(start);
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");

        WeeklySheet weekly = weeklySheetService.findWeeklySheetByDateInWeek(thisWeeksMonday, emp);

        map.put("weekly", weekly);
        map.put("message", "success");
        return map;
    }

    @RequestMapping("/intervals/weekly/submit")
    @ResponseBody
    public Map<String, Object> submitWeekly(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");

        if (emp != null && JobType.DIVISION_MANAGER.equals(emp.getJob())) {
            map.put("message", "success");
            return map;
        }

        String _start = request.getParameter("start");
        if (_start == null || "undefined".equals(_start)) {
            map.put("message", "failed");
            return map;
        }

        Date start = new Date(Long.parseLong(_start));
        Date lastWeeksMonday = DateUtils.findThisWeeksMonday(start);
        WeeklySheet last = weeklySheetService.findWeeklySheetByDateInWeek(lastWeeksMonday, emp);

        if (last != null && !WeeklyActivityStatus.SUBMITTED_PENDING.equals(last.getStatus())) {
            last.setStatus(WeeklyActivityStatus.SUBMITTED_PENDING);

            weeklySheetService.save(last);

            Notification notif = new Notification();
            notif.setIssue_time(new Date());
            notif.setMessage("I've worked hard!");
            notif.setSheet(last);
            notif.setIsSeen(false);
            notif.setType(NotificationType.EMP2MGR);

            if (JobType.DEPARTMENT_MANAGER.equals(emp.getJob())) {
                notif.setReviewingManager(emp.getDepartment().getDivision().getManager());
            }
            else {
                notif.setReviewingManager(emp.getDepartment().getManager());
            }

            notificationService.save(notif);
        }

        map.put("message", "success");
        return map;
    }

    @RequestMapping(value = "/intervals/weekly/approve", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> approveWeekly(HttpServletRequest request, HttpServletResponse response, @RequestParam Long weeklyId, @RequestParam String action, @RequestParam Long notificationId) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (weeklyId == null || action == null || "undefined".equals(action) || action.length() < 1) {
            map.put("message", "failed");
            return map;
        }

        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        if (emp == null || (!emp.getJob().equals(JobType.DEPARTMENT_MANAGER) && !emp.getJob().equals(JobType.DIVISION_MANAGER))) {
            map.put("message", "failed");
            return map;
        }

        WeeklySheet weekly = weeklySheetService.findWeeklySheetById(weeklyId);
        if (weekly == null) {
            map.put("message", "failed");
            return map;
        }

        Notification notification = notificationService.findNotificationById(notificationId);

        if (notification == null) {
            map.put("message", "failed");
            return map;
        }
        notification.setType(NotificationType.MGR2EMP);

        if (action.equals("approve")) {
            weekly.setStatus(WeeklyActivityStatus.OK);
            notification.setMessage("Good job!");
        }
        else if (action.equals("reject")) {
            weekly.setStatus(WeeklyActivityStatus.REJECTED);
            notification.setMessage("Are you sure?");
        }

        weeklySheetService.save(weekly);
        notificationService.save(notification);

        map.put("message", "success");
        return map;
    }

    @RequestMapping(value = "/intervals/employee/activities/week", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getActivitiesPerWeek(HttpServletRequest request, HttpServletResponse response, @RequestParam String _weeklyId) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (_weeklyId == null || "undefined".equals(_weeklyId)) {
            map.put("message", "failed");
            return map;
        }

        List<Activity> activities = activityService.findAllByWeekly(Long.parseLong(_weeklyId));

        map.put("events", activities);
        map.put("message", "success");
        return map;
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

                WeeklySheet ws = weeklySheetService.findWeeklySheetByDateInWeek(s, emp);
                if (ws == null) {
                    ws = new WeeklySheet();
                    ws.setOwner(emp);
                    ws.setStartingDay(DateUtils.findThisWeeksMonday(s));
                    ws.setStatus(WeeklyActivityStatus.OPEN);
                    weeklySheetService.save(ws);
                }
                activity.setWeeklySheet(ws);
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