package com.airvals.model;

import com.intervals.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 13/Jun/2014
 */

@Entity
@Table(name = "av_flight_template")
public class FlightTemplate extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Airport source;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Airport destination;

    @Column(name = "days_of_week")
    private String daysOfWeek;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_time")
    private Date departureTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_time")
    private Date arrivalTime;

    @JoinColumn(name = "plane_id")
    @ManyToOne
    private Plane plane;

    @JoinColumn(name = "company_id")
    @ManyToOne
    private Company company;

    @Column(name = "base_eur_price")
    private Float baseEURPrice;

    public Airport getSource() {
        return source;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Float getBaseEURPrice() {
        return baseEURPrice;
    }

    public void setBaseEURPrice(Float baseEURPrice) {
        this.baseEURPrice = baseEURPrice;
    }

}
