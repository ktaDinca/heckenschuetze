package com.intervals.service.impl;

import com.intervals.dao.WeeklySheetDao;
import com.intervals.model.Employee;
import com.intervals.model.WeeklySheet;
import com.intervals.service.WeeklySheetService;
import com.intervals.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */
@Service
public class WeeklySheetServiceImpl implements WeeklySheetService {

    @Autowired
    private WeeklySheetDao weeklySheetDao;

    @Override
    public WeeklySheet findWeeklySheetByDateInWeek(Date d, Employee emp) {
        Date w = DateUtils.findThisWeeksMonday(d);
        return weeklySheetDao.findWeeklySheetByStartingDateAndOwner(w, emp);
    }

    @Override
    public void save(WeeklySheet ws) {
        weeklySheetDao.saveOrUpdate(ws);
    }

}
