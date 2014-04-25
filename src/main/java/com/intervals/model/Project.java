package com.intervals.model;

import javax.persistence.*;

@Entity
@Table(name = "ts_project")
public class Project extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public Client getClient() {
        return client;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
