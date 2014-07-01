package com.airvals.dao;

import com.airvals.model.Airport;
import com.airvals.model.City;
import com.airvals.model.Flight;
import com.intervals.dao.BaseDao;
import com.intervals.util.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
@Repository
public class FlightDao extends BaseDao {

    public List<Flight> findDirectFlightsSourceDestination(City source, City dest, Date departure, Integer seats) {

        Date endOfDepartureDate = DateUtils.getEndOfTheDay(departure);

        Query q = entityManager.createQuery(
                "Select f " +
                        "from Flight f " +
                        "where f.template.source.city.id = :source_id and " +
                        "f.template.destination.city.id = :dest_id and " +
                        "f.departure > :departure and f.departure < :endOfDepartureDay and " +
                        "f.occupiedPositions + :seatsWanted < f.template.plane.capacity"
        );

        q.setParameter("source_id", source.getId());
        q.setParameter("dest_id", dest.getId());
        q.setParameter("departure", departure);
        q.setParameter("seatsWanted", seats);
        q.setParameter("endOfDepartureDay", endOfDepartureDate);

        return q.getResultList();
    }

    /*
        This method is used to find outbound flights from a City.
        It excludes all the direct flights to the destination City.
     */
    public List<Flight> findValidFlightsLeavingFrom(City sourceCity, City destinationCity, Date departureDate, Integer seats) {
        Date endOfDepartureDate = DateUtils.getEndOfTheDay(departureDate);

        Query q = entityManager.createQuery("Select f from Flight f " +
                "where f.template.source.id = :sourceId and f.template.destination.id <> :destinationId and " +
                "f.departure > :departureDate and f.departure < :endOfDay and " +
                "f.occupiedPositions + :seatsWanted < f.template.plane.capacity");

        q.setParameter("sourceId", sourceCity.getId());
        q.setParameter("destinationId", destinationCity.getId());
        q.setParameter("departureDate", departureDate);
        q.setParameter("endOfDay", endOfDepartureDate);
        q.setParameter("seatsWanted", seats);

        return q.getResultList();
    }

    public List<Flight> findValidFlightsArrivingAt(City sourceCity, City destinationCity, Date departureDate, Integer seats) {
        Date endOfDepartureDate = DateUtils.getEndOfTheDay(departureDate);

        Query q = entityManager.createQuery("Select f from Flight f " +
                "where f.template.destination.id = :destinationId and f.template.source.id <> :sourceId and " +
                "f.departure > :departureDate and f.departure < :endOfDay and " +
                "f.occupiedPositions + :seatsWanted < f.template.plane.capacity");

        q.setParameter("destinationId", destinationCity.getId());
        q.setParameter("sourceId", sourceCity.getId());
        q.setParameter("departureDate", departureDate);
        q.setParameter("endOfDay", endOfDepartureDate);
        q.setParameter("seatsWanted", seats);

        return q.getResultList();
    }

    public Flight findById(Long id) {
        Query q = entityManager.createQuery("Select f from Flight f where f.id = :id");
        q.setParameter("id", id);

        List<Flight> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}