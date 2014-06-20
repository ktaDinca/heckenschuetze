package com.airvals.service.impl;

import com.airvals.dao.PlaneDao;
import com.airvals.model.Plane;
import com.airvals.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 13/Jun/2014
 */
@Service
public class PlaneServiceImpl implements PlaneService {

    @Autowired
    private PlaneDao planeDao;

    @Override
    public List<Plane> loadAll() {
        return planeDao.loadAll();
    }

    @Override
    public Plane findPlaneById(Long id) {
        if (id == null) {
            return null;
        }
        return planeDao.findPlaneById(id);
    }
}
