package com.intervals.dao;

import com.intervals.model.Employee;
import com.intervals.model.WeeklySheet;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */
@Repository
public class WeeklySheetDao extends BaseDao {

    public WeeklySheet findWeeklySheetByStartingDateAndOwner(Date d, Employee e) {
        Query q = entityManager.createQuery("Select w from WeeklySheet w where w.startingDay = :starting and w.owner.id = :id");
        q.setParameter("starting", d);
        q.setParameter("id", e.getId());
        List<WeeklySheet> results = q.getResultList();

        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public WeeklySheet findWeeklySheetById(Long id) {
        Query q = entityManager.createQuery("Select w from WeeklySheet w where w.id = :id");
        q.setParameter("id", id);

        List<WeeklySheet> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
