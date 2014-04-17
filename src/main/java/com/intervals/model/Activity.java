package com.intervals.model;

import javax.persistence.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since March 3, 2014
 */
//@Entity
//@Table(name = "ts_activity")
public class Activity extends AbstractEntity {

    @Column(name = "duration")
    private Float duration;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dtimesheet_id")
    private DailyTimeSheet timesheet;

    @Column(name = "is_extra")
    private Boolean isExtra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public Float getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public DailyTimeSheet getTimesheet() {
        return timesheet;
    }

    public Boolean getIsExtra() {
        return isExtra;
    }

    public Project getProject() {
        return project;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimesheet(DailyTimeSheet timesheet) {
        this.timesheet = timesheet;
    }

    public void setIsExtra(Boolean isExtra) {
        this.isExtra = isExtra;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
