package com.airvals.service.impl;

import com.airvals.dao.FlightTemplateDao;
import com.airvals.model.FlightTemplate;
import com.airvals.service.FlightTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 14/Jun/2014
 */
@Service
public class FlightTemplateServiceImpl implements FlightTemplateService {

    @Autowired
    private FlightTemplateDao flightTemplateDao;

    @Override
    public void save(FlightTemplate flightTemplate) {
        flightTemplateDao.saveOrUpdate(flightTemplate);
    }

    @Override
    public List<FlightTemplate> loadAll() {
        return flightTemplateDao.loadAll();
    }

    @Override
    public FlightTemplate findTemplateById(Long id) {

        return flightTemplateDao.findTemplateById(id);

    }
}
