package com.airvals.dao;

import com.airvals.model.Airport;
import com.airvals.model.City;
import com.intervals.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
@Repository
public class AirportDao extends BaseDao {

    public List<Airport> findAirportsByCity(City c) {

        Query q = entityManager.createQuery("Select a from Airport a where a.city.id = :id order by a.name");
        q.setParameter("id", c.getId());

        return q.getResultList();
    }

    public List<Airport> loadAll() {
        Query q = entityManager.createQuery("Select a from Airport a order by a.name");
        return q.getResultList();
    }

    public Airport findAirportById(Long id) {
        Query q = entityManager.createQuery("Select a from Airport a where a.id = :id");
        q.setParameter("id", id);

        List<Airport> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
