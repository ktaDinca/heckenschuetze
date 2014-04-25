package com.intervals.service.impl;

import com.intervals.dao.DepartmentDao;
import com.intervals.model.Department;
import com.intervals.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 06/Apr/2014
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public void save(Department dept) {
        departmentDao.saveOrUpdate(dept);
    }

    @Override
    public Department findDepartmentByName(String name) {
        return departmentDao.findByName(name);
    }

    @Override
    public List<Department> loadAll() {
        return departmentDao.loadAll();
    }

    @Override
    public Department findDepartmentById(Long dept_id) {
        if (dept_id != null) {
            return departmentDao.findDepartmentById(dept_id);
        }
        return null;
    }

    @Override
    public void remove(Long deptid) {
        if (deptid != null) {
            departmentDao.remove(deptid);
        }
    }
}
