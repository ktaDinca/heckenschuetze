package com.intervals.service;

import com.intervals.model.Department;
import com.intervals.model.Project;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Apr/2014
 */
public interface ProjectService {

    public List<Project> loadAll();

    public void save(Project p);

    Project findById(Long projectId);

    void remove(Long projectId);

    List<Project> findByDepartment(Department department);
}
