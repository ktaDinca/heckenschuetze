package com.airvals.service;

import com.airvals.model.*;

import java.util.Date;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
public interface FlightService {

    public List<Flight> findValidFlights(City source, City destination, Date departure, Integer seats);

    void generateFlights(FlightTemplate template, Date start, Date end);

    void save(Flight f);

    List<FlightResult> findOnlyDirectFlights(City sourceCity, City destinationCity, Date departureDate, Integer seats);

    List<FlightResult> processOneWayAndOneStopFlights(City sourceCity, City destinationCity, Date departureDate, Integer seats);

    List<FlightResult> processRoundTripAndDirectFlights(City sourceCity, City destinationCity, Date departureDate, Date returningDate, Integer seats);

    List<FlightResult> processRoundTripAndOneStopFlights(City sourceCity, City destinationCity, Date departureDate, Date returningDate, Integer seats);

    List<FlightResult> findIndicatorsForFlights(List<FlightResult> results, Boolean isOneWay, Boolean isDirectFlight);

    List<FlightResult> findOnlyIndirectFlights(City sourceCity, City destinationCity, Date departureDate, Integer seats);
}
