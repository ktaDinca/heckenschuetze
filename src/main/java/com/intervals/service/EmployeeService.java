package com.intervals.service;


import com.intervals.model.Employee;
import com.intervals.model.JobType;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Feb/2014
 */
public interface EmployeeService {

    public void save(Employee user);

    public Employee findEmployeeByUsername(String username);

    public boolean checkEmployeeAccess(Employee user, String password);

    public List<Employee> loadAll();

    public Employee findEmployeeById(Long emp_id);

    public List<Employee> loadAllEmployeesByJob(JobType job);

    void remove(Long empid);
}
