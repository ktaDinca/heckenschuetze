package com.airvals.model;

import com.intervals.model.AbstractEntity;

import javax.persistence.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 27/May/2014
 */
@Entity
@Table(name = "av_airport")
public class Airport extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "full_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id ")
    private City city;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
