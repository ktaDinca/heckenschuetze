package com.intervals.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since March 3, 2014
 */
//@Entity
//@Table(name = "ts_daily_timesheet")
public class DailyTimeSheet extends AbstractEntity {

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
	private Date date;

    @ManyToOne
    @Column(name = "owner_id")
	private Employee owner;

    @ManyToOne
    @JoinColumn(name = "month_timesheet_id")
    private MonthlyTimesheet mTimesheet;

    public Date getDate() {
        return date;
    }

    public Employee getOwner() {
        return owner;
    }

    public MonthlyTimesheet getmTimesheet() {
        return mTimesheet;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public void setmTimesheet(MonthlyTimesheet mTimesheet) {
        this.mTimesheet = mTimesheet;
    }

}
