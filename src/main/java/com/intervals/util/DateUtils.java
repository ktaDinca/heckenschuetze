package com.intervals.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */
public class DateUtils {

    /**
     * Go backwards in time to find this week's Monday
     * @param d
     * @return
     */
    public static Date findThisWeeksMonday(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);

        return calendar.getTime();
    }

    /**
     *
     * @param d - day in this week
     * @return - previous week's Monday
     */
    public static Date findLastWeeksMonday(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(findThisWeeksMonday(d));

        cal.add(Calendar.DAY_OF_MONTH, -7);
        return cal.getTime();
    }
}
