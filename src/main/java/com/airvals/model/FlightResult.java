package com.airvals.model;

import com.intervals.model.AbstractEntity;

import javax.persistence.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
@Entity
@Table(name = "av_flight_result")
public class FlightResult extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "outbound_step_1_id")
    private Flight outBoundStep1;

    @ManyToOne
    @JoinColumn(name = "outbound_step_2_id")
    private Flight outBoundStep2;

    @ManyToOne
    @JoinColumn(name = "inbound_step_1_id")
    private Flight inBoundStep1;

    @ManyToOne
    @JoinColumn(name = "inbound_step_2_id")
    private Flight inBoundStep2;

    @Column(name = "price")
    private Float price;

    public FlightResult() {}

    public FlightResult(Flight goStep1, Flight goStep2, Flight backStep1, Flight backStep2) {
        this.outBoundStep1 = goStep1;
        this.outBoundStep2 = goStep2;
        this.inBoundStep1 = backStep1;
        this.inBoundStep2 = backStep2;
    }

    public Flight getOutBoundStep1() {
        return outBoundStep1;
    }

    public void setOutBoundStep1(Flight outBoundStep1) {
        this.outBoundStep1 = outBoundStep1;
    }

    public Flight getOutBoundStep2() {
        return outBoundStep2;
    }

    public void setOutBoundStep2(Flight outBoundStep2) {
        this.outBoundStep2 = outBoundStep2;
    }

    public Flight getInBoundStep1() {
        return inBoundStep1;
    }

    public void setInBoundStep1(Flight inBoundStep1) {
        this.inBoundStep1 = inBoundStep1;
    }

    public Flight getInBoundStep2() {
        return inBoundStep2;
    }

    public void setInBoundStep2(Flight inBoundStep2) {
        this.inBoundStep2 = inBoundStep2;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
