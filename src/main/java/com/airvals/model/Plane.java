package com.airvals.model;

import com.intervals.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 06/Jun/2014
 */
@Entity
@Table(name = "av_plane")
public class Plane extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
