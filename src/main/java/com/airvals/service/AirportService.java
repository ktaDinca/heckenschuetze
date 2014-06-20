package com.airvals.service;

import com.airvals.model.Airport;
import com.airvals.model.City;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
public interface AirportService {

    public List<Airport> findAirportsByCity(City c);

    List<Airport> loadAll();

    Airport findAirportById(Long id);
}
