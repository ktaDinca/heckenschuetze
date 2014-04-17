package com.intervals.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since March 3, 2014
 */
@Entity
@Table(name = "ts_department")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="json_id")
public class Department extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn (name = "manager_id")
//    @JsonBackReference
//    @JsonManagedReference
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    public String getName() {
        return name;
    }

    public Employee getManager() {
        return manager;
    }

    public Division getDivision() {
        return division;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

}
