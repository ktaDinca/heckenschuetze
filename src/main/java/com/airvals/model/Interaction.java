package com.airvals.model;

import com.intervals.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 03/Iul/2014
 */
@Entity
@Table(name = "av_interaction")
public class Interaction extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "action")
    private String action;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private City source;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private City destination;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "price")
    private Float price;

    public Interaction() {}

    public Interaction(User user, String action, City source, City destination, Date date, Float price) {
        this.user = user;
        this.action = action;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public City getSource() {
        return source;
    }

    public void setSource(City source) {
        this.source = source;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
