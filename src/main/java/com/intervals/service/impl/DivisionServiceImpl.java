package com.intervals.service.impl;

import com.intervals.dao.DivisionDao;
import com.intervals.model.Department;
import com.intervals.model.Division;
import com.intervals.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 06/Apr/2014
 */

@Service
public class DivisionServiceImpl implements DivisionService {

    @Autowired
    private DivisionDao divisionDao;

    @Override
    public void save(Division div) {
        if (div != null) {
            divisionDao.saveOrUpdate(div);
        }
        else {
            throw new NullPointerException("trying to persist a null Division");
        }
    }

    @Override
    public Division findDivisionByName(String name) {
        return divisionDao.findByName(name);
    }

    @Override
    public List<Division> loadAll() {
        return divisionDao.loadAll();
    }

    @Override
    public Division findDivisionById(Long id) {
        return divisionDao.findById(id);
    }
}
