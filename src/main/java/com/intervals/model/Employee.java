package com.intervals.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 19/Mar/2014
 */
@Entity
@Table(name = "ts_employee")
public class Employee extends AbstractEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "hiredate")
    @Temporal(TemporalType.DATE)
    private Date hiredate;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @Column(name = "job")
    @Enumerated(EnumType.STRING)
    private JobType job;

    public Department getDepartment() {
        return department;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public JobType getJob() {
        return job;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setJob(JobType job) {
        this.job = job;
    }
}
