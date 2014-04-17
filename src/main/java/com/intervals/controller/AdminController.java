package com.intervals.controller;

import com.intervals.model.Department;
import com.intervals.model.Division;
import com.intervals.model.Employee;
import com.intervals.model.JobType;
import com.intervals.service.DepartmentService;
import com.intervals.service.DivisionService;
import com.intervals.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 08/Apr/2014
 */

@Controller
public class AdminController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DivisionService divisionService;

    @RequestMapping (value= "/admin/employees", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmployees(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Employee> emps = employeeService.loadAll();

        map.put("employees", emps);

        return map;
    }

    @RequestMapping(value = "/admin/employees/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object > edit(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();


        String username = request.getParameter("username");

        String password = request.getParameter("password");
        String lastname = request.getParameter("lastname");
        String firstname = request.getParameter("firstname");
        String email = request.getParameter("email");

        JobType job = null;
        String jobStr = request.getParameter("job");
        if (jobStr != null && !jobStr.equals("") && !jobStr.trim().equals("select")) {
            job = JobType.valueOf(jobStr);
        }

        Long dept_id = Long.parseLong(request.getParameter("dept"));

        String id = request.getParameter("id");
        Long emp_id = null;
        if (id != "" && !"undefined".equals(id)) {
            emp_id = Long.parseLong(id);
        }

        Employee emp;
        if (emp_id != null) {
            emp = employeeService.findEmployeeById(emp_id);
        }
        else {
            emp = new Employee();
        }

        if (emp != null) {
            if (username != null && !username.equals(emp.getUsername())) {
                emp.setUsername(username);
            }
            if (lastname != null && !lastname.equals(emp.getLastname())) {
                emp.setLastname(lastname);
            }
            if (firstname!= null && !firstname.equals(emp.getFirstname())) {
                emp.setFirstname(firstname);
            }
            if (email != null && !email.equals(emp.getEmail())) {
                emp.setEmail(email);
            }
            if (password != null && password.length() > 0) {
                emp.setPassword(password);
            }
            if (dept_id != null) {
                if (emp.getDepartment() == null || (emp.getDepartment() != null && !dept_id.equals(emp.getDepartment().getId()))) {
                    Department dept = departmentService.findDepartmentById(dept_id);
                    emp.setDepartment(dept);
                }
            }
            if (job != null) {
                emp.setJob(job);
            }
            employeeService.save(emp);
        }

        map.put("message", "success");
        return map;
    }

    @RequestMapping(value = "/admin/employees/dept-managers", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDeptManagers(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Employee> deptManagers = employeeService.loadAllEmployeesByJob(JobType.DEPARTMENT_MANAGER);

        map.put("deptManagers", deptManagers);
        return map;
    }

    @RequestMapping(value = "/admin/employees/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeEmployee(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();

        String id = request.getParameter("id");
        Long empid = null;
        if (id != null && id.length() > 0 && !"undefined".equals(id)) {
            empid = Long.parseLong(id);

            employeeService.remove(empid);
            map.put("message", "success");
            return map;
        }
        map.put("message", "failed");
        return map;
    }


    @RequestMapping(value="/admin/departments", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDepartments(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Department> deps = departmentService.loadAll();

        map.put("departments", deps);
        return map;
    }

    @RequestMapping(value = "/admin/departments/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editDepartment(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();


        String id = request.getParameter("id");
        Long dept_id = null;
        if (id != null && id.length() > 0 && !"undefined".equals(id)) {
            dept_id = Long.parseLong(id);
        }

        String dname = request.getParameter("name");
        Long manager_id = Long.parseLong(request.getParameter("manager_id"));
        Long division_id = Long.parseLong(request.getParameter("division_id"));

        Department dept = null;
        if (dept_id != null) {
            dept = departmentService.findDepartmentById(dept_id);
        }
        else {
            dept = new Department();
        }

        if (dept != null) {
            if (dname != null && !dname.equals(dept.getName())) {
                dept.setName(dname);
            }

            if (manager_id != null) {
                Employee manager = employeeService.findEmployeeById(manager_id);
                dept.setManager(manager);
            }

            if (division_id != null) {
                Division division = divisionService.findDivisionById(division_id);
                dept.setDivision(division);
            }

            departmentService.save(dept);
            map.put("message", "success");
        }
        else {
            map.put("message", "fail");
        }

        return map;
    }

    @RequestMapping(value = "/admin/divisions", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDivisions(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Division> divisions = divisionService.loadAll();

        map.put("divisions", divisions);
        return map;
    }

    @RequestMapping(value = "/admin/divisions/division-managers", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDivisionManagers(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Employee> divisionManagers = employeeService.loadAllEmployeesByJob(JobType.DIVISION_MANAGER);
        map.put("divisionManagers", divisionManagers);
        return map;
    }

    @RequestMapping(value = "/admin/divisions/edit")
    @ResponseBody
    public Map<String, Object> editDivision(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();


        String divid = request.getParameter("id");
        Long divId = null;
        if (divid != null && divid.length() > 0 && !"undefined".equals(divid)) {
            divId = Long.parseLong(divid);
        }
        String name = request.getParameter("name");

        String manager_id = request.getParameter("manager_id");

        Division div = null;
        if (divId != null) {
            div = divisionService.findDivisionById(divId);
        }
        else {
            div = new Division();
        }

        if (div != null) {
            if (name != null) {
                div.setName(name);
            }
            if (manager_id != null && manager_id.length() > 0 && !"undefined".equals(manager_id)) {
                Employee manager = employeeService.findEmployeeById(Long.parseLong(manager_id));

                div.setManager(manager);
            }

            divisionService.save(div);
        }

        map.put("message", "success");
        return map;
    }


}
