package com.intervals.service.impl;

import com.intervals.dao.EmployeeDao;
import com.intervals.model.Employee;
import com.intervals.model.JobType;
import com.intervals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Feb/2014
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void save(Employee user) {
        employeeDao.save(user);

    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        return employeeDao.findEmployeeByUsername(username);
    }

    @Override
    public boolean checkEmployeeAccess(Employee emp, String password) {
        if (emp == null) {
            return false;
        }
        PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        if (emp.getPassword().equals(passwordEncoder.encodePassword(password, null))) {
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> loadAll() {
        return employeeDao.loadAll();
    }

    @Override
    public Employee findEmployeeById(Long emp_id) {
        if (emp_id == null) {
            return null;
        }
        return employeeDao.findEmployeeById(emp_id);
    }

    @Override
    public List<Employee> loadAllEmployeesByJob(JobType job) {
        return employeeDao.findAllEmployeesByJob(job);
    }

    @Override
    public void remove(Long empid) {
        employeeDao.remove(empid);
    }


}
