package com.airvals.service;

import com.airvals.model.FlightResult;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 22/Jun/2014
 */
public interface FlightResultService {

    public void saveOrUpdate(FlightResult fr);

    void incrementAllFlightsOccupiedSeats(FlightResult fr);
}
