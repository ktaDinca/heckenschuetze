package com.intervals.service;

import com.intervals.model.Activity;
import com.intervals.model.Employee;

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
}
