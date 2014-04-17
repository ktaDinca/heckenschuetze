package com.intervals.dao;


import com.intervals.model.DailyTimeSheet;
import com.intervals.model.Employee;
import com.intervals.model.MonthlyTimesheet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class DailyTimeSheetDao extends BaseDao {

    public DailyTimeSheet getDailyTimeSheetByDate(Date d) {
        System.out.println("BEWARE, THIS IS THE DUMMY METHOD!");
        return null;
    }

    public DailyTimeSheet getDailyTimeSheetByDateAndUser(Date d, Employee u) {
        if (d == null || u == null || u.getId() == null) {
            return null;
        }

        Query q = entityManager.createQuery("Select d from DailyTimeSheet d where d.date = :date and d.owner.id = :id");
        q.setParameter("date", d);
        q.setParameter("id", u.getId());

        if (q.getResultList().size() > 0) {
            return (DailyTimeSheet) q.getResultList().get(0);
        }
        return null;
    }

    public void removeDailyTimeSheetActivity(Date d, Employee e) {
        Query q = entityManager.createQuery("Delete DailyTimeSheet d where d.date = :date and d.owner.id = :id");
        q.setParameter("date", d);
        q.setParameter("id", e.getId());
        q.executeUpdate();
    }

    public void forcedAttributeSetter(DailyTimeSheet dts, Date d, Employee e, MonthlyTimesheet m) {
        if (dts == null || dts.getId() == null) {
            return;
        }

        StringBuilder query = new StringBuilder("Update DailyTimeSheet set ");
        if (d != null) {
            query.append("date = :date ");
        }
        if (e != null && e.getId() != null) {
            query.append(", owner.id = :emp_id ");
        }
        if (m != null && m.getId() != null) {
            query.append(", month_timesheet_id = :month_id ");
        }
        query.append("where id = :id");
        Query q = entityManager.createQuery(query.toString());

        if (d != null)
            q.setParameter("date", d);
        if (e != null && e.getId() != null)
            q.setParameter("emp_id", e.getId());
        if (m != null && m.getId() != null)
            q.setParameter("month_id", m.getId());
        if (dts != null && dts.getId() != null)
            q.setParameter("id", dts.getId());

        q.executeUpdate();
    }

    public List<DailyTimeSheet> findDTSbyMTS(MonthlyTimesheet mts) {
        Query q = entityManager.createQuery("Select d from DailyTimeSheet d where d.mTimesheet.id = :id").setParameter("id", mts.getId());
        return q.getResultList();
    }

    public List<DailyTimeSheet> loadFromInterval(Employee e, Date from, Date to) {
        Query q = entityManager.createQuery("Select d from DailyTimeSheet d where d.date > :from and d.date < :to and d.owner.id = :id");
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("id", e.getId());

        return q.getResultList();
    }
}
