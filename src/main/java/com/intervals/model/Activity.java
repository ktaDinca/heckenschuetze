package com.intervals.model;

import freemarker.template.SimpleDate;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since March 3, 2014
 */
@Entity
@Table(name = "ts_activity")
public class Activity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "owner")
    private Employee owner;

    @Column(name = "description")
    private String description;

    @Column(name = "start")
    private Date start;

    @Column(name = "end")
    private Date end;

    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

    public Employee getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Project getProject() {
        return project;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
