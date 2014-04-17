package com.intervals.model;

import javax.persistence.*;

@Entity
@Table(name = "ts_division")
public class Division extends AbstractEntity {

	@Column(name = "name")
	private String name;

	@OneToOne
	@JoinColumn(name = "manager_id")
	private Employee manager;

    public String getName() {
        return name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

}
