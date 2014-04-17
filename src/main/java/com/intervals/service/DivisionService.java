package com.intervals.service;

import com.intervals.model.Department;
import com.intervals.model.Division;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 06/Apr/2014
 */
public interface DivisionService {

    public void save(Division div);

    public Division findDivisionByName(String name);

    List<Division> loadAll();

    Division findDivisionById(Long id);
}
