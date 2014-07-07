package com.intervals.service.impl;

import com.intervals.dao.ProjectDao;
import com.intervals.model.Department;
import com.intervals.model.Project;
import com.intervals.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Apr/2014
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<Project> loadAll() {
        return projectDao.loadAll();
    }

    @Override
    public void save(Project p) {
        projectDao.saveOrUpdate(p);
    }

    @Override
    public Project findById(Long projectId) {
        return projectDao.loadById(projectId);
    }

    @Override
    public void remove(Long projectId) {
        projectDao.remove(projectId);
    }

    @Override
    public List<Project> findByDepartment(Department department) {
        if (department == null) {
            return null;
        }
        return projectDao.findProjectsForDepartment(department);
    }
}
