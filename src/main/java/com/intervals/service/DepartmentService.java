package com.intervals.service;

import com.intervals.model.Department;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 06/Apr/2014
 */
public interface DepartmentService {

    public void save(Department dept);

    public Department findDepartmentByName(String name);

    public List<Department> loadAll();

    Department findDepartmentById(Long dept_id);

    void remove(Long deptid);
}
