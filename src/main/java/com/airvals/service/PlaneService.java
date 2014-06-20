package com.airvals.service;

import com.airvals.model.Plane;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 13/Jun/2014
 */
public interface PlaneService {

    public List<Plane> loadAll();

    Plane findPlaneById(Long id);
}
