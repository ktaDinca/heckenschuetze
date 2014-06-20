package com.airvals.controller;

import com.airvals.model.*;
import com.airvals.service.*;
import com.intervals.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
@Controller
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private FlightTemplateService flightTemplateService;

    @Autowired
    private PlaneService planeService;

    @RequestMapping(value = "/airvals/flights/search", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchFlights(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String _source = request.getParameter("source");
        City sourceCity = null;
        if (StringUtils.isNotEmptyNullOrUndefined(_source)) {
            sourceCity = cityService.findByName(_source);
            if (sourceCity == null) {
                map.put("message", "failed");
                return map;
            }
        }

        String _destination = request.getParameter("destination");
        City destinationCity = null;
        if (StringUtils.isNotEmptyNullOrUndefined(_destination)) {
            destinationCity = cityService.findByName(_destination);
            if (destinationCity == null) {
                map.put("message", "failed");
                return map;
            }
        }

        Boolean isOneWay = null;
        String _isOneWay = request.getParameter("isOneWay");
        if (StringUtils.isNotEmptyNullOrUndefined(_isOneWay)) {
            isOneWay = new Boolean(_isOneWay);
        }

        Boolean isDirectFlight = null;
        String _isDirectFlight = request.getParameter("isDirectFlight");
        if (StringUtils.isNotEmptyNullOrUndefined(_isDirectFlight)) {
            isDirectFlight = new Boolean(_isDirectFlight);
        }

        String _departureDate = request.getParameter("departureDate");
        Date departureDate = null;
        if(StringUtils.isNotEmptyNullOrUndefined(_departureDate) && !"NaN".equals(_departureDate)) {
            departureDate = new Date(Long.parseLong(_departureDate));
        }
        else {
            map.put("message", "failed");
            return map;
        }

        String _returningDate = request.getParameter("returningDate");
        Date returningDate = null;
        if (!isOneWay) {
            if (StringUtils.isNotEmptyNullOrUndefined(_returningDate) && !"NaN".equals(_returningDate)) {
                returningDate = new Date(Long.parseLong(_returningDate));
            } else {
                map.put("message", "failed");
                return map;
            }
        }

        List<FlightResult> results = new ArrayList<FlightResult>();
        FlightResult fastestTrip = null;
        FlightResult bestPrice = null;
        FlightResult maximisedTime = null;

        if (isOneWay && isDirectFlight) {
            results = flightService.findOnlyDirectFlights(sourceCity, destinationCity, departureDate, 1);
        }
        if (isOneWay && !isDirectFlight) {
            results = flightService.processOneWayAndOneStopFlights(sourceCity, destinationCity, departureDate, 1);
        }
        if (!isOneWay && isDirectFlight) {
            results = flightService.processRoundTripAndDirectFlights(sourceCity, destinationCity, departureDate, returningDate, 1);
        }
        if (!isOneWay && !isDirectFlight) {
            results = flightService.processRoundTripAndOneStopFlights(sourceCity, destinationCity, departureDate, returningDate, 1);
        }

        List<FlightResult> flightIndicators = null;
        if (results.size() > 0) {
            flightIndicators = flightService.findIndicatorsForFlights(results, isOneWay, isDirectFlight);
            fastestTrip = flightIndicators.get(0);
            bestPrice = flightIndicators.get(1);
            maximisedTime = flightIndicators.get(2) != null ? flightIndicators.get(2) : fastestTrip ;
        }

        map.put("fastestTrip", fastestTrip);
        map.put("lowestPrice", bestPrice);
        map.put("maximisedTime", maximisedTime);

        map.put("message", "success");
        map.put("flights", results);

        return map;
    }

    @RequestMapping(value = "/airvals/flight/generate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> generateFlights(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String _templateId = request.getParameter("templateId");
        FlightTemplate template = flightTemplateService.findTemplateById(Long.parseLong(_templateId));

        String _start = request.getParameter("start");
        Date start = new Date(Long.parseLong(_start));

        String _end = request.getParameter("end");
        Date end = new Date(Long.parseLong(_end));

        flightService.generateFlights(template, start, end);

        map.put("message", "success");
        return map;
    }


    @RequestMapping(value = "/airvals/template/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveTemplate(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        FlightTemplate template = new FlightTemplate();
        StringBuilder error = new StringBuilder();

        Airport departureAirport = null;
        String _departureId = request.getParameter("departureId");
        if (StringUtils.isNotEmptyNullOrUndefined("_departureId")) {
            departureAirport = airportService.findAirportById(Long.parseLong(_departureId));
            template.setSource(departureAirport);
        }
        else {
            error.append("* Departure airport is invalid\n");
        }

        Airport arrivalAirport = null;
        String _arrivalId = request.getParameter("arrivalId");
        if (StringUtils.isNotEmptyNullOrUndefined("_arrivalId")) {
            arrivalAirport = airportService.findAirportById(Long.parseLong(_arrivalId));
            template.setDestination(arrivalAirport);
        }
        else {
            error.append("* Arrival airport is invalid\n");
        }

        Plane plane = null;
        String _planeId = request.getParameter("planeId");
        if (StringUtils.isNotEmptyNullOrUndefined("_planeId")) {
            plane = planeService.findPlaneById(Long.parseLong(_planeId));
            template.setPlane(plane);
        }
        else {
            error.append("* Plane Type selected is invalid\n");
        }

        Date departure = null;
        String _departureTime = request.getParameter("departureTime");
        if (StringUtils.isNotEmptyNullOrUndefined("_departureTime")) {
            departure = new Date(Long.parseLong(_departureTime));
            template.setDepartureTime(departure);
        }
        else {
            error.append("* No departure time provided\n");
        }

        Date arrival = null;
        String _arrivalTime = request.getParameter("arrivalTime");
        if (StringUtils.isNotEmptyNullOrUndefined("_arrivalTime")) {
            arrival = new Date(Long.parseLong(_arrivalTime));
            template.setArrivalTime(arrival);
        }
        else {
            error.append("* No arrival time provided\n");
        }

        String days = request.getParameter("days");
        if (StringUtils.isNotEmptyNullOrUndefined(days)) {
            template.setDaysOfWeek(days);
        }
        else {
            error.append("* No valid week days provided\n");
        }

        flightTemplateService.save(template);

        map.put("message", "success");
        map.put("error", error);

        return map;
    }

    @RequestMapping(value = "/airvals/template/load", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadTemplates(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<FlightTemplate> templates = flightTemplateService.loadAll();

        map.put("message", "success");
        map.put("templates", templates);

        return map;
    }

}
