package com.airvals.dao;

import com.airvals.model.FlightTemplate;
import com.intervals.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 14/Jun/2014
 */

@Repository
public class FlightTemplateDao extends BaseDao{

    public List<FlightTemplate> loadAll() {
        Query q = entityManager.createQuery("Select t from FlightTemplate t");
        return q.getResultList();
    }

    public FlightTemplate findTemplateById(Long id) {
        Query q = entityManager.createQuery("Select t from FlightTemplate t where t.id = :id");
        q.setParameter("id", id);

        List<FlightTemplate> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
