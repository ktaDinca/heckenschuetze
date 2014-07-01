package com.airvals.dao;

import com.airvals.model.WeekdayPercentMap;
import com.intervals.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 22/Jun/2014
 */
@Repository
public class WeekdayPercentMapDao extends BaseDao {

    public WeekdayPercentMap getWPMbyDay(String day) {
        Query q = entityManager.createQuery("Select wp from WeekdayPercentMap wp where wp.day = :day");
        q.setParameter("day", day);

        List<WeekdayPercentMap> results = q.getResultList();

        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public List<WeekdayPercentMap> getAll() {
        Query q = entityManager.createQuery("Select a from WeekdayPercentMap a");
        return q.getResultList();
    }
}
