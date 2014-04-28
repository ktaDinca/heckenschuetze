package com.intervals.service;

import com.intervals.model.Employee;
import com.intervals.model.WeeklySheet;

import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */
public interface WeeklySheetService {

    /**
     *
     * @param d - day of a week
     * @param emp
     * @return - the WeeklySheet that contains it.
     */
    public WeeklySheet findWeeklySheetByDateInWeek(Date d, Employee emp);

    void save(WeeklySheet ws);
}
