package com.airvals.service;

import com.airvals.model.FlightTemplate;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 14/Jun/2014
 */
public interface FlightTemplateService {

    void save(FlightTemplate flightTemplate);

    List<FlightTemplate> loadAll();

    FlightTemplate findTemplateById(Long l);
}
