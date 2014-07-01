package com.airvals.service.impl;

import com.airvals.dao.FlightDao;
import com.airvals.dao.WeekdayPercentMapDao;
import com.airvals.model.*;
import com.airvals.service.FlightService;
import com.intervals.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private WeekdayPercentMapDao weekdayPercentMapDao;

    @Override
    public List<Flight> findValidFlights(City source, City destination, Date departure, Integer seats) {
        if (source == null || destination == null || departure == null) {
            return null;
        }
        return flightDao.findDirectFlightsSourceDestination(source, destination, departure, seats);
    }

    @Override
    public void generateFlights(FlightTemplate template, Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        Date iter = start;

        Map<Integer, Boolean> daysOfTheWeekMap = new HashMap<Integer, Boolean>();
        for (int i = 0; i < template.getDaysOfWeek().length() - 1; i++) {
            if ("1".equals("" + template.getDaysOfWeek().charAt(i))) {
                daysOfTheWeekMap.put(i + 2, true);
            } else {
                daysOfTheWeekMap.put(i + 2, false);
            }
        }

        if ("1".equals("" + template.getDaysOfWeek().charAt(6))) {
            daysOfTheWeekMap.put(1, true);
        } else {
            daysOfTheWeekMap.put(1, false);
        }

        calendar.setTime(iter);
        while (iter.before(end)) {
            if (daysOfTheWeekMap.get(calendar.get(Calendar.DAY_OF_WEEK)) == true) {
                save(createFlight(template, iter));
            }

            calendar.add(Calendar.DATE, 1);
            iter = calendar.getTime();
            calendar.setTime(iter);
        }

    }

    @Override
    public void save(Flight f) {
        flightDao.saveOrUpdate(f);
    }

    /**
     * This method can be used to find only the direct flights between 2 airports
     * @param sourceCity
     * @param destinationCity
     * @param departureDate
     * @param seats
     * @return
     */
    @Override
    public List<FlightResult> findOnlyDirectFlights(City sourceCity, City destinationCity, Date departureDate, Integer seats) {

        // all direct flights from source -> destination
        List<Flight> flights = flightDao.findDirectFlightsSourceDestination(sourceCity, destinationCity, departureDate, seats);
        List<FlightResult> results = new ArrayList<FlightResult>();

        FlightResult temp = null;
        for (Flight f : flights) {
            temp = new FlightResult(f, null, null, null);
            results.add(temp);
            temp.setPrice(computeFlightResultPrice(temp));
        }
        return results;
    }

    private Float computeFlightResultPrice(FlightResult flightResult) {
        Float price = new Float(0);

        if (flightResult.getOutBoundStep1() != null) {
            price += computeIndividualFlightPrice(flightResult.getOutBoundStep1());
        }

        if (flightResult.getOutBoundStep2() != null) {
            price += computeIndividualFlightPrice(flightResult.getOutBoundStep2()) / 2;
        }

        if (flightResult.getInBoundStep1() != null) {
            price += computeIndividualFlightPrice(flightResult.getInBoundStep1());
        }

        if (flightResult.getInBoundStep2() != null) {
            price += computeIndividualFlightPrice(flightResult.getInBoundStep2()) / 2;
        }

        return price;
    }

    public Float computeIndividualFlightPrice(Flight f) {
        Float price = new Float(0);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String flightDay = sdf.format(f.getDeparture());

        List<WeekdayPercentMap> map = weekdayPercentMapDao.getAll();

        price += f.getTemplate().getBaseEURPrice();
//        price += f.getTemplate().getBaseEURPrice() * weekdayPercentMapDao.getWPMbyDay(flightDay).getPercent() / 100;
//
//        price -= f.getTemplate().getBaseEURPrice() * (5 * (DateUtils.getDateDiff(new Date(), f.getDeparture(), TimeUnit.DAYS) / 30)) / 100;

        return price;
    }


    /**
     * This method is used to find BOTH direct and with one stop flights from source to destination
     * @param sourceCity
     * @param destinationCity
     * @param departureDate
     * @param seats
     * @return
     */
    @Override
    public List<FlightResult> processOneWayAndOneStopFlights(City sourceCity, City destinationCity, Date departureDate, Integer seats) {

        //doar dus dar accepta si zboruri cu escala.
        List<FlightResult> results = new ArrayList<FlightResult>();

        List<Flight> directFlights = flightDao.findDirectFlightsSourceDestination(sourceCity, destinationCity, departureDate, seats);
        FlightResult temp = null;
        for (Flight f : directFlights) {
            temp = new FlightResult(f, null, null, null);
            temp.setPrice(computeFlightResultPrice(temp));
            results.add(temp);
        }

        List<Flight> indirectFlights = new ArrayList<Flight>();

        List<Flight> indirectFlightsStep1 = flightDao.findValidFlightsLeavingFrom(sourceCity, destinationCity, departureDate, seats);
        List<Flight> indirectFlightsStep2 = flightDao.findValidFlightsArrivingAt(sourceCity, destinationCity, departureDate, seats);

        for (Flight f1 : indirectFlightsStep1) {
            for (Flight f2 : indirectFlightsStep2) {
                if (f1.getTemplate().getDestination().getId().equals(f2.getTemplate().getSource().getId())) {
                    temp = new FlightResult(f1, f2, null, null);
                    temp.setPrice(computeFlightResultPrice(temp));
                    results.add(temp);
                }
            }
        }

        return results;
    }

    @Override
    public List<FlightResult> processRoundTripAndDirectFlights(City sourceCity, City destinationCity, Date departureDate, Date returningDate, Integer seats) {
        List<FlightResult> results = new ArrayList<FlightResult>();

        List<Flight> directOutBoundFlights = flightDao.findDirectFlightsSourceDestination(sourceCity, destinationCity, departureDate, seats);
        List<Flight> directInBoundFlights = flightDao.findDirectFlightsSourceDestination(destinationCity, sourceCity, returningDate, seats);

        FlightResult temp = null;
        for (Flight out : directOutBoundFlights) {
            for (Flight in : directInBoundFlights) {
                temp = new FlightResult(out, null, in, null);
                temp.setPrice(computeFlightResultPrice(temp));
                results.add(temp);
            }
        }
        return results;
    }

    @Override
    public List<FlightResult> processRoundTripAndOneStopFlights(City sourceCity, City destinationCity, Date departureDate, Date returningDate, Integer seats) {
        List<FlightResult> results = new ArrayList<FlightResult>();

        List<FlightResult> outDirect = findOnlyDirectFlights(sourceCity, destinationCity, departureDate, seats);
        List<FlightResult> outIndirect = findOnlyIndirectFlights(sourceCity, destinationCity, departureDate, seats);

        List<FlightResult> inDirect = findOnlyDirectFlights(destinationCity, sourceCity, returningDate, seats);
        List<FlightResult> inIndirect = findOnlyIndirectFlights(destinationCity, sourceCity, returningDate, seats);

        // 1. outDirect x inDirect
        FlightResult temp = null;
        for (FlightResult frOut : outDirect) {
            for (FlightResult frIn : inDirect) {
                temp = new FlightResult(frOut.getOutBoundStep1(), frOut.getOutBoundStep2(), frIn.getOutBoundStep1(), frIn.getOutBoundStep2());
                temp.setPrice(computeFlightResultPrice(temp));
                results.add(temp);
            }
        }

        // 2. outDirect x inIndirect
        for (FlightResult frOut : outDirect) {
            for (FlightResult frIn : inIndirect) {
                temp = new FlightResult(frOut.getOutBoundStep1(), frOut.getOutBoundStep2(), frIn.getOutBoundStep1(), frIn.getOutBoundStep2());
                temp.setPrice(computeFlightResultPrice(temp));
                results.add(temp);
            }
        }

        // 3. outIndirect x inDirect
        for (FlightResult frOut : outIndirect) {
            for (FlightResult frIn : inDirect) {
                temp = new FlightResult(frOut.getOutBoundStep1(), frOut.getOutBoundStep2(), frIn.getOutBoundStep1(), frIn.getOutBoundStep2());
                temp.setPrice(computeFlightResultPrice(temp));
                results.add(temp);
            }
        }

        // 4. outIndirect x inIndirect
        for (FlightResult frOut : outIndirect) {
            for (FlightResult frIn : inIndirect) {
                temp = new FlightResult(frOut.getOutBoundStep1(), frOut.getOutBoundStep2(), frIn.getOutBoundStep1(), frIn.getOutBoundStep2());
                temp.setPrice(computeFlightResultPrice(temp));
                results.add(temp);
            }
        }

        return results;
    }

    @Override
    public List<FlightResult> findIndicatorsForFlights(List<FlightResult> results, Boolean isOneWay, Boolean isDirectFlight) {

        List<FlightResult> returnList = new ArrayList<FlightResult>();

        FlightResult maximisedTime = null;
        FlightResult fastestTrip = null;
        FlightResult bestPrice = null;

        Long currentTimeDiff = null;
        Long minimumTimeDiff = null;
        Long minimumPrice = null;
        Long currentPrice = null;
        Date earliestFlightDeparture = null;

        if (isOneWay && isDirectFlight) {
            minimumTimeDiff = DateUtils.getDateDiff(results.get(0).getOutBoundStep1().getDeparture(), results.get(0).getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES);
            minimumPrice = results.get(0).getOutBoundStep1().getPrice();
            earliestFlightDeparture = results.get(0).getOutBoundStep1().getDeparture();

            for (FlightResult f : results) {
                currentTimeDiff = DateUtils.getDateDiff(f.getOutBoundStep1().getDeparture(), f.getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES);
                if (currentTimeDiff <= minimumTimeDiff) {
                    fastestTrip = f;
                    minimumTimeDiff = currentTimeDiff;

                }
                if (f.getOutBoundStep1().getPrice() <= minimumPrice) {
                    bestPrice = f;
                    minimumPrice = f.getOutBoundStep1().getPrice();
                }
                if (f.getOutBoundStep1().getDeparture().before(earliestFlightDeparture)) {
                    maximisedTime = f;
                    earliestFlightDeparture = f.getOutBoundStep1().getDeparture();
                }
            }
        } else if (isOneWay && !isDirectFlight) {
            minimumTimeDiff = results.get(0).getOutBoundStep2() == null ? DateUtils.getDateDiff(results.get(0).getOutBoundStep1().getDeparture(), results.get(0).getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) :
                    DateUtils.getDateDiff(results.get(0).getOutBoundStep1().getDeparture(), results.get(0).getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) +
                            DateUtils.getDateDiff(results.get(0).getOutBoundStep2().getDeparture(), results.get(0).getOutBoundStep2().getDueArrival(), TimeUnit.MINUTES);

            minimumPrice = results.get(0).getOutBoundStep2() == null ? results.get(0).getOutBoundStep1().getPrice() : results.get(0).getOutBoundStep1().getPrice() +
                    results.get(0).getOutBoundStep2().getPrice();

            earliestFlightDeparture = results.get(0).getOutBoundStep1().getDeparture();

            for (FlightResult f : results) {
                currentTimeDiff = f.getOutBoundStep2() == null ? DateUtils.getDateDiff(f.getOutBoundStep1().getDeparture(), f.getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) :
                        DateUtils.getDateDiff(f.getOutBoundStep1().getDeparture(), f.getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) +
                                DateUtils.getDateDiff(f.getOutBoundStep2().getDeparture(), f.getOutBoundStep2().getDueArrival(), TimeUnit.MINUTES);

                if (currentTimeDiff <= minimumTimeDiff) {
                    fastestTrip = f;
                    minimumTimeDiff = currentTimeDiff;
                }

                currentPrice = f.getOutBoundStep2() == null ? f.getOutBoundStep1().getPrice() : f.getOutBoundStep1().getPrice() +
                        f.getOutBoundStep2().getPrice();
                if (currentPrice <= minimumPrice) {
                    bestPrice = f;
                    minimumPrice = currentPrice;
                }
                if (f.getOutBoundStep1().getDeparture().before(earliestFlightDeparture)) {
                    maximisedTime = f;
                    earliestFlightDeparture = f.getOutBoundStep1().getDeparture();
                }
            }
        } else if (!isOneWay && isDirectFlight) {
            minimumTimeDiff = DateUtils.getDateDiff(results.get(0).getOutBoundStep1().getDeparture(), results.get(0).getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) +
                    DateUtils.getDateDiff(results.get(0).getInBoundStep1().getDeparture(), results.get(0).getInBoundStep1().getDueArrival(), TimeUnit.MINUTES);

            minimumPrice = results.get(0).getOutBoundStep1().getPrice() + results.get(0).getInBoundStep1().getPrice();

            earliestFlightDeparture = results.get(0).getOutBoundStep1().getDeparture();

            for (FlightResult f : results) {
                currentTimeDiff = DateUtils.getDateDiff(f.getOutBoundStep1().getDeparture(), f.getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES);
                if (currentTimeDiff <= minimumTimeDiff) {
                    fastestTrip = f;
                    minimumTimeDiff = currentTimeDiff;

                }
                if (f.getOutBoundStep1().getPrice() <= minimumPrice) {
                    bestPrice = f;
                    minimumPrice = f.getOutBoundStep1().getPrice();
                }
                if (f.getOutBoundStep1().getDeparture().before(earliestFlightDeparture)) {
                    maximisedTime = f;
                    earliestFlightDeparture = f.getOutBoundStep1().getDeparture();
                }
            }
        } else if (!isOneWay && !isDirectFlight) {
            minimumTimeDiff = results.get(0).getOutBoundStep2() == null ? DateUtils.getDateDiff(results.get(0).getOutBoundStep1().getDeparture(), results.get(0).getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) :
                    DateUtils.getDateDiff(results.get(0).getOutBoundStep1().getDeparture(), results.get(0).getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) +
                            DateUtils.getDateDiff(results.get(0).getOutBoundStep2().getDeparture(), results.get(0).getOutBoundStep2().getDueArrival(), TimeUnit.MINUTES);

            minimumTimeDiff += results.get(0).getInBoundStep2() == null ? DateUtils.getDateDiff(results.get(0).getInBoundStep1().getDeparture(), results.get(0).getInBoundStep1().getDueArrival(), TimeUnit.MINUTES) :
                    DateUtils.getDateDiff(results.get(0).getInBoundStep1().getDeparture(), results.get(0).getInBoundStep1().getDueArrival(), TimeUnit.MINUTES) +
                            DateUtils.getDateDiff(results.get(0).getInBoundStep2().getDeparture(), results.get(0).getInBoundStep2().getDueArrival(), TimeUnit.MINUTES);

            minimumPrice = results.get(0).getOutBoundStep2() == null ? results.get(0).getOutBoundStep1().getPrice() : results.get(0).getOutBoundStep1().getPrice() +
                    results.get(0).getOutBoundStep2().getPrice();
            minimumPrice += results.get(0).getInBoundStep2() == null ? results.get(0).getInBoundStep1().getPrice() : results.get(0).getInBoundStep1().getPrice() +
                    results.get(0).getInBoundStep2().getPrice();

            earliestFlightDeparture = results.get(0).getOutBoundStep1().getDeparture();

            for (FlightResult f : results) {
                currentTimeDiff = f.getOutBoundStep2() == null ? DateUtils.getDateDiff(f.getOutBoundStep1().getDeparture(), f.getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) :
                        DateUtils.getDateDiff(f.getOutBoundStep1().getDeparture(), f.getOutBoundStep1().getDueArrival(), TimeUnit.MINUTES) +
                                DateUtils.getDateDiff(f.getOutBoundStep2().getDeparture(), f.getOutBoundStep2().getDueArrival(), TimeUnit.MINUTES);

                currentTimeDiff += f.getInBoundStep2() == null ? DateUtils.getDateDiff(f.getInBoundStep1().getDeparture(), f.getInBoundStep1().getDueArrival(), TimeUnit.MINUTES) :
                        DateUtils.getDateDiff(f.getInBoundStep1().getDeparture(), f.getInBoundStep1().getDueArrival(), TimeUnit.MINUTES) +
                                DateUtils.getDateDiff(f.getInBoundStep2().getDeparture(), f.getInBoundStep2().getDueArrival(), TimeUnit.MINUTES);

                if (currentTimeDiff <= minimumTimeDiff) {
                    fastestTrip = f;
                    minimumTimeDiff = currentTimeDiff;
                }

                currentPrice = f.getOutBoundStep2() == null ? f.getOutBoundStep1().getPrice() : f.getOutBoundStep1().getPrice() +
                        f.getOutBoundStep2().getPrice();
                currentPrice += f.getInBoundStep2() == null ? f.getInBoundStep1().getPrice() : f.getInBoundStep1().getPrice() +
                        f.getInBoundStep2().getPrice();


                if (currentPrice <= minimumPrice) {
                    bestPrice = f;
                    minimumPrice = currentPrice;
                }
                if (f.getOutBoundStep1().getDeparture().before(earliestFlightDeparture)) {
                    maximisedTime = f;
                    earliestFlightDeparture = f.getOutBoundStep1().getDeparture();
                }
            }
        }

        returnList.add(fastestTrip);
        returnList.add(bestPrice);
        returnList.add(maximisedTime);

        return returnList;
    }

    /**
     * This method can be used to find only the indirect flights between 2 airports
     * @param sourceCity
     * @param destinationCity
     * @param departureDate
     * @param seats
     * @return
     */
    @Override
    public List<FlightResult> findOnlyIndirectFlights(City sourceCity, City destinationCity, Date departureDate, Integer seats) {
        List<FlightResult> results = new ArrayList<FlightResult>();

        FlightResult temp = null;

        List<Flight> indirectFlights = new ArrayList<Flight>();

        List<Flight> indirectFlightsStep1 = flightDao.findValidFlightsLeavingFrom(sourceCity, destinationCity, departureDate, seats);
        List<Flight> indirectFlightsStep2 = flightDao.findValidFlightsArrivingAt(sourceCity, destinationCity, departureDate, seats);

        for (Flight f1 : indirectFlightsStep1) {
            for (Flight f2 : indirectFlightsStep2) {
                if (f1.getTemplate().getDestination().getId().equals(f2.getTemplate().getSource().getId())) {
                    temp = new FlightResult(f1, f2, null, null);
                    results.add(temp);
                }
            }
        }

        return results;
    }

    @Override
    public Flight findByid(Long l) {
        if (l == null) {
            return null;
        }
        return flightDao.findById(l);
    }

    private Flight createFlight(FlightTemplate template, Date iter) {
        Calendar calendar = Calendar.getInstance();

        Flight f = new Flight();
        f.setTemplate(template);
        f.setOccupiedPositions(0);

        // pune ziua corecta
        calendar.setTime(iter);
        calendar.set(Calendar.HOUR_OF_DAY, template.getDepartureTime().getHours());
        calendar.set(Calendar.MINUTE, template.getDepartureTime().getMinutes());
        f.setDeparture(calendar.getTime());

        f.setPrice(new Long(100));

        calendar.set(Calendar.HOUR_OF_DAY, template.getArrivalTime().getHours());
        calendar.set(Calendar.MINUTE, template.getArrivalTime().getMinutes());
        f.setDueArrival(calendar.getTime());

        return f;
    }
}
