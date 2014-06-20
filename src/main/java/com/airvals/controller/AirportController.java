package com.airvals.controller;

import com.airvals.model.Airport;
import com.airvals.model.Plane;
import com.airvals.service.AirportService;
import com.airvals.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 13/Jun/2014
 */
@Controller
public class AirportController {

    @Autowired
    private AirportService airportService;

    @Autowired
    private PlaneService planeService;

    @RequestMapping(value = "/airvals/airport/load", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAirports(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Airport> airports = airportService.loadAll();

        map.put("message", "success");
        map.put("airports", airports);
        return map;
    }

    @RequestMapping(value = "/airvals/plane/load", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPlaneTypes(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Plane> planes = planeService.loadAll();

        map.put("message", "success");
        map.put("planes", planes);

        return map;
    }

}
