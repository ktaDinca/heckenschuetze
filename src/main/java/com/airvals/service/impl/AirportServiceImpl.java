package com.airvals.service.impl;

import com.airvals.dao.AirportDao;
import com.airvals.model.Airport;
import com.airvals.model.City;
import com.airvals.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportDao airportDao;

    @Override
    public List<Airport> findAirportsByCity(City c) {
        if (c == null || c.getId() == null) {
            return null;
        }
        return airportDao.findAirportsByCity(c);
    }

    @Override
    public List<Airport> loadAll() {
        return airportDao.loadAll();
    }

    @Override
    public Airport findAirportById(Long id) {
        if (id == null) {
            return null;
        }
        return airportDao.findAirportById(id);

    }
}
