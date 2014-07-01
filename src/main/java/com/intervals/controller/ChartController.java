package com.intervals.controller;

import com.intervals.model.Activity;
import com.intervals.model.Employee;
import com.intervals.model.WeeklySheet;
import com.intervals.service.ActivityService;
import com.intervals.service.WeeklySheetService;
import com.intervals.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 29/Apr/2014
 */

@Controller
public class ChartController {

    @Autowired
    private WeeklySheetService weeklySheetService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = "/intervals/charts/weekly/project-hours-donut", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loadProjectWorkForCurrentDonutChart(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object>  map = new HashMap<String, Object>();

        String start = request.getParameter("start");
        Date day = null;
        if (start == null || "undefined".equals(start)) {
            map.put("message", "failed");
            return map;
        }

        day = new Date(Long.parseLong(start));
        Date thisWeeksMonday = DateUtils.findThisWeeksMonday(day);
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        WeeklySheet currentWeekly = weeklySheetService.findWeeklySheetByDateInWeek(thisWeeksMonday, emp);
        if (currentWeekly == null) {
            map.put("message", "failed");
            return map;
        }

        List<Activity> currentActivities = activityService.findAllByWeekly(currentWeekly);
        Map<String, Long> projectHoursMap = new HashMap<String, Long>();
        for (Activity a : currentActivities) {
            if (a.getProject() == null) {
                continue;
            }
            if (projectHoursMap.get(a.getProject().getName()) == null) {
                projectHoursMap.put(a.getProject().getName(), DateUtils.getDateDiff(a.getStart(), a.getEnd(), TimeUnit.HOURS));
            }
            else {
                Long oldValue = projectHoursMap.get(a.getProject().getName());
                projectHoursMap.put(a.getProject().getName(), oldValue + DateUtils.getDateDiff(a.getStart(), a.getEnd(), TimeUnit.HOURS));
            }
        }

        map.put("projectHours", projectHoursMap);
        map.put("message", "success");
        return map;
    }

    @RequestMapping(value = "/intervals/charts/weekly/working-hours-line", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loadWorkingHoursForWeekly(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String start = request.getParameter("start");
        Date day = null;
        if (start == null || "undefined".equals(start)) {
            map.put("message", "failed");
            return map;
        }

        day = new Date(Long.parseLong(start));
        Date thisWeeksMonday = DateUtils.findThisWeeksMonday(day);
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        WeeklySheet currentWeekly = weeklySheetService.findWeeklySheetByDateInWeek(thisWeeksMonday, emp);
        if (currentWeekly == null) {
            map.put("message", "failed");
            return map;
        }
        List<Activity> currentActivities = activityService.findAllByWeekly(currentWeekly);

        // ziua (monday), ora de inceput (8:40).
        Map<String, Date> startingHoursMap = new HashMap<String, Date>();
        Map<String, Date> endingHoursMap = new HashMap<String, Date>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Activity activity : currentActivities) {

            if(startingHoursMap.get(DateUtils.goTodayAt1Am(activity.getStart())) == null) {
                startingHoursMap.put(sdf.format(DateUtils.goTodayAt1Am(activity.getStart())), activity.getStart());
            }
            else {
                if (activity.getStart().before(startingHoursMap.get(DateUtils.goTodayAt1Am(activity.getStart())))) {
                    startingHoursMap.put(sdf.format(DateUtils.goTodayAt1Am(activity.getStart())), activity.getStart());
                }
            }

            if(endingHoursMap.get(DateUtils.goTodayAt1Am(activity.getEnd())) == null) {
                endingHoursMap.put(sdf.format(DateUtils.goTodayAt1Am(activity.getEnd())), activity.getEnd());
            }
            else {
                if (activity.getEnd().before(endingHoursMap.get(DateUtils.goTodayAt1Am(activity.getEnd())))) {
                    endingHoursMap.put(sdf.format(DateUtils.goTodayAt1Am(activity.getEnd())), activity.getEnd());
                }
            }
        }

        map.put("startings", startingHoursMap);
        map.put("endings", endingHoursMap);
        map.put("message", "success");

        return map;
    }

}
