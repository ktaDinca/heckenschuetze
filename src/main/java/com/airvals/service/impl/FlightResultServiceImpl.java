package com.airvals.service.impl;

import com.airvals.dao.FlightDao;
import com.airvals.dao.FlightResultDao;
import com.airvals.model.FlightResult;
import com.airvals.service.FlightResultService;
import com.airvals.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 22/Jun/2014
 */
@Service
public class FlightResultServiceImpl implements FlightResultService {

    @Autowired
    private FlightResultDao flightResultDao;

    @Autowired
    private FlightDao flightDao;

    public void saveOrUpdate(FlightResult fr) {
        flightResultDao.saveOrUpdate(fr);
    }

    @Override
    public void incrementAllFlightsOccupiedSeats(FlightResult fr) {
        if (fr == null) {
            return;
        }
        if (fr.getOutBoundStep1() != null) {
            fr.getOutBoundStep1().setOccupiedPositions(fr.getOutBoundStep1().getOccupiedPositions() + 1);
            flightDao.saveOrUpdate(fr.getOutBoundStep1());
        }
        if (fr.getOutBoundStep2() != null) {
            fr.getOutBoundStep2().setOccupiedPositions(fr.getOutBoundStep2().getOccupiedPositions() + 1);
            flightDao.saveOrUpdate(fr.getOutBoundStep2());
        }
        if (fr.getInBoundStep1() != null) {
            fr.getInBoundStep1().setOccupiedPositions(fr.getInBoundStep1().getOccupiedPositions() + 1);
            flightDao.saveOrUpdate(fr.getInBoundStep1());
        }
        if (fr.getInBoundStep2() != null) {
            fr.getInBoundStep2().setOccupiedPositions(fr.getInBoundStep2().getOccupiedPositions() + 1);
            flightDao.saveOrUpdate(fr.getInBoundStep2());
        }
        saveOrUpdate(fr);
    }

}
