package com.intervals.model;

import javax.persistence.*;
import java.util.Date;

//@Entity
//@Table(name = "ts_monthly_timesheet")
public class MonthlyTimesheet extends AbstractEntity {

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MonthlyTimesheetStatus status = MonthlyTimesheetStatus.OPEN;

    public Date getDate() {
        return date;
    }

    public Employee getOwner() {
        return owner;
    }

    public MonthlyTimesheetStatus getStatus() {
        return status;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public void setStatus(MonthlyTimesheetStatus status) {
        this.status = status;
    }
}
