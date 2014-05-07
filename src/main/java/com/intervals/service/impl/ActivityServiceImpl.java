package com.intervals.service.impl;

import com.intervals.dao.ActivityDao;
import com.intervals.model.Activity;
import com.intervals.model.Employee;
import com.intervals.model.WeeklySheet;
import com.intervals.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 25/Apr/2014
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Override
    public Activity findActivityById(Long activityId) {
        return activityDao.findById(activityId);
    }

    @Override
    public void save(Activity activity) {
        activityDao.saveOrUpdate(activity);
    }

    @Override
    public List<Activity> findAllByUser(Employee emp) {
        return activityDao.findAllByUser(emp);
    }

    @Override
    public void removeById(Long actId) {
        activityDao.removeById(actId);
    }

    @Override
    public List<Activity> findAllByWeekly(WeeklySheet currentWeekly) {
        if (currentWeekly == null) {
            return null;
        }
        return activityDao.findAllByWeekly(currentWeekly);
    }

    @Override
    public List<Activity> findAllByWeekly(Long weeklyId) {
        if (weeklyId == null) {
            return null;
        }
        return activityDao.findAllByWeeklyId(weeklyId);
    }
}
