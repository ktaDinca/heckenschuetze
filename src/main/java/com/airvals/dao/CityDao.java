package com.airvals.dao;

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
public class CityDao extends BaseDao {

    public List<City> loadAll() {
        Query q = entityManager.createQuery("Select c from City c order by c.name");
        return q.getResultList();
    }

    public City findByName(String source) {
        Query q = entityManager.createQuery("Select c from City c where c.name = :name");
        q.setParameter("name", source);

        List<City> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
