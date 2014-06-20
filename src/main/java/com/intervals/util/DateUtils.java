package com.intervals.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
     * Goes back x hours to 01:00 AM
     * @param d
     * @return
     */
    public static Date goTodayAt1Am(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);

        calendar.set(Calendar.HOUR_OF_DAY, 1);
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

    /**
     * Computes the difference in hours between 2 dates.
     * @param date1 - start
     * @param date2 - end
     * @return
     */
    public static Long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        Long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Date getEndOfTheDay(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);

        return cal.getTime();
    }

    public static String getDayInTheWeek(Integer index) {
        switch (index) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return null;
        }
    }


}
