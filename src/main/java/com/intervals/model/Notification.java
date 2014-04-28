package com.intervals.model;


import javax.persistence.*;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */
@Entity
@Table(name = "ts_notifications")
public class Notification extends AbstractEntity {

    @Temporal(TemporalType.DATE)
    @Column(name = "issue_time")
    private Date issue_time;

    @JoinColumn(name = "weeklysheet_id")
    @ManyToOne
    private WeeklySheet sheet;

    @JoinColumn(name = "review_mgr_id")
    @ManyToOne
    private Employee reviewingManager;

    @Column(name = "message")
    private String message;

    @Column(name = "is_seen")
    private Boolean isSeen;

    @Column(name = "type")
    private NotificationType type;

    public Boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(Boolean isSeen) {
        this.isSeen = isSeen;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Date getIssue_time() {
        return issue_time;
    }

    public void setIssue_time(Date issue_time) {
        this.issue_time = issue_time;
    }

    public WeeklySheet getSheet() {
        return sheet;
    }

    public void setSheet(WeeklySheet sheet) {
        this.sheet = sheet;
    }

    public Employee getReviewingManager() {
        return reviewingManager;
    }

    public void setReviewingManager(Employee reviewingManager) {
        this.reviewingManager = reviewingManager;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
