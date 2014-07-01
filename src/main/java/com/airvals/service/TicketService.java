package com.airvals.service;

import com.airvals.model.FlightResult;
import com.airvals.model.Person;
import com.airvals.model.Ticket;
import com.itextpdf.text.Document;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Jun/2014
 */
public interface TicketService {

    public void saveOrUpdate(Ticket t);

    String buildDocument(FlightResult fr, Person p, String code, String action);
}
