package com.intervals.service;

import com.intervals.model.Activity;
import com.intervals.model.Employee;
import com.intervals.model.Project;
import com.intervals.model.WeeklySheet;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 25/Apr/2014
 */

public interface ActivityService {

    Activity findActivityById(Long activityId);

    void save(Activity activity);

    List<Activity> findAllByUser(Employee emp);

    void removeById(Long actId);

    List<Activity> findAllByWeekly(WeeklySheet currentWeekly);

    List<Activity> findAllByWeekly(Long weeklyId);

    List<Activity> findAllByProject(Project p);
}
