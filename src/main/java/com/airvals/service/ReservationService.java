package com.airvals.service;

import com.airvals.model.FlightResult;
import com.airvals.model.Person;
import com.airvals.model.Reservation;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Jun/2014
 */
public interface ReservationService {


    void saveOrUpdate(Reservation reservation);
}
