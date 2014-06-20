package com.airvals.model;

import com.intervals.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 25/May/2014
 */

@Entity
@Table(name = "av_flight")
public class Flight extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "template_id")
    private FlightTemplate template;

    @Column(name = "departure")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departure;

    @Column(name = "arrival")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueArrival;

    @Column(name = "price")
    private Long price;

    @Column(name = "occupied_positions")
    private Integer occupiedPositions;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    public FlightTemplate getTemplate() {
        return template;
    }

    public Date getDeparture() {
        return departure;
    }

    public Date getDueArrival() {
        return dueArrival;
    }

    public Integer getOccupiedPositions() {
        return occupiedPositions;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setTemplate(FlightTemplate template) {
        this.template = template;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public void setDueArrival(Date dueArrival) {
        this.dueArrival = dueArrival;
    }

    public void setOccupiedPositions(Integer occupiedPositions) {
        this.occupiedPositions = occupiedPositions;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
