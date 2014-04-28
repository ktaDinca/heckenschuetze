package com.intervals.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */

@Entity
@Table(name = "ts_weekly_act")
public class WeeklySheet extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;

    @Temporal(TemporalType.DATE)
    @Column(name = "starting_day")
    private Date startingDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WeeklyActivityStatus status;

    public Employee getOwner() {
        return owner;
    }

    public Date getStartingDay() {
        return startingDay;
    }

    public WeeklyActivityStatus getStatus() {
        return status;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public void setStartingDay(Date startingDay) {
        this.startingDay = startingDay;
    }

    public void setStatus(WeeklyActivityStatus status) {
        this.status = status;
    }

}
