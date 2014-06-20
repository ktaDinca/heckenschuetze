package com.airvals.service;

import com.airvals.dao.CityDao;
import com.airvals.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
public interface CityService {

    public List<City> loadAll();

    City findByName(String source);
}
