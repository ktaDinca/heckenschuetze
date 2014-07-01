package com.airvals.service.impl;

import com.airvals.dao.ReservationDao;
import com.airvals.model.Reservation;
import com.airvals.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Jun/2014
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public void saveOrUpdate(Reservation reservation) {
        reservationDao.saveOrUpdate(reservation);
    }
}
