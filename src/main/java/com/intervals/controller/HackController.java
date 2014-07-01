package com.intervals.controller;

import com.intervals.model.Department;
import com.intervals.model.Division;
import com.intervals.model.Employee;
import com.intervals.model.JobType;
import com.intervals.service.DepartmentService;
import com.intervals.service.DivisionService;
import com.intervals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Mar/2014
 */
@Controller
public class HackController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value="/create-default-department")
    public void createDefaultDepartment(HttpServletRequest request, HttpServletResponse response) {

        Department dept = departmentService.findDepartmentByName("J2ee");
        if (dept == null) {
            dept = new Department();
            dept.setName("J2ee");

            Division div = divisionService.findDivisionByName("IT");

            if (div == null) {
                return ;
            }

            dept.setDivision(div);
            departmentService.save(dept);
        }
    }

    @RequestMapping("/create-default-division")
    private void createDefaultDivision(HttpServletRequest request, HttpServletResponse response){
        Division div = divisionService.findDivisionByName("IT");
        if (div == null) {
            div = new Division();
            div.setName("IT");
            divisionService.save(div);
        }
    }

    @RequestMapping("/create-default-users")
    private void createDefaultUsers(HttpServletRequest request, HttpServletResponse response) {

        Employee emp;
        emp = employeeService.findEmployeeByUsername("cdinca");
        if (emp == null) {
            emp = new Employee();
            emp.setUsername("cdinca");
            emp.setJob(JobType.CLERK);

            Department dept = departmentService.findDepartmentByName("J2ee");
            if (dept == null) {
                return;
            }

            emp.setDepartment(dept);

            PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
            emp.setPassword(passwordEncoder.encodePassword("cdinca", null));
            employeeService.save(emp);
        }

        emp = null;
        emp = employeeService.findEmployeeByUsername("abogza");
        if (emp == null) {
            emp = new Employee();
            emp.setUsername("abogza");
            emp.setJob(JobType.ADMIN);

            Department dept = departmentService.findDepartmentByName("J2ee");
            if (dept == null) {
                return;
            }

            emp.setDepartment(dept);

            PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
            emp.setPassword(passwordEncoder.encodePassword("abogza", null));
            employeeService.save(emp);
        }
    }
}
