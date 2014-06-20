package com.airvals.service.impl;

import com.airvals.dao.CityDao;
import com.airvals.model.City;
import com.airvals.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> loadAll() {
        return cityDao.loadAll();
    }

    @Override
    public City findByName(String source) {
        return cityDao.findByName(source);
    }
}
