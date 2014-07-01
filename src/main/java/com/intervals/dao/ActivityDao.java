package com.intervals.dao;

import com.intervals.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ActivityDao extends BaseDao {


    public Activity findByDateDurationDescAndProject(Date date, Float duration, String description, Project p) {

        Query q = entityManager.createQuery("Select a from Activity a where a.timesheet.date = :date and a.duration = :duration and a.description = :description and a.project.id = :project_id");
        q.setParameter("date", date).setParameter("duration", duration).setParameter("description", description)
                .setParameter("project_id", p.getId());

        ArrayList<Activity> results = (ArrayList<Activity>) q.getResultList();

        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public void removeByDateDurationDescAndProject(Activity a) {
        entityManager.getTransaction().begin();
        entityManager.remove(a);
        entityManager.getTransaction().commit();
    }

    public List<Activity> findWorkPutIntoProject(Project p, Date from, Date to) {
        Query q = entityManager.createQuery("Select m from Activity m where m.timesheet.date > :from and m.timesheet.date < :to and m.project.id = :pid");
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("pid", p.getId());

        return q.getResultList();
    }

    public List<Activity> findActivitiesByProject(Project p) {
        Query q = entityManager.createQuery("Select m from Activity m where m.project.id = :pid");
        q.setParameter("pid", p.getId());
        return q.getResultList();
    }

    public Activity findByDateDurationDesc(Date date, Float duration,
                                           String description) {

        Query q = entityManager.createQuery("Select a from Activity a where a.timesheet.date = :date and a.duration = :duration and a.description = :description");
        q.setParameter("date", date).setParameter("duration", duration).setParameter("description", description);

        ArrayList<Activity> results = (ArrayList<Activity>) q.getResultList();

        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public Activity findById(Long activityId) {
        Query q = entityManager.createQuery("Select a from Activity a where a.id = :id").setParameter("id", activityId);
        List<Activity> results = q.getResultList();

        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public List<Activity> findAllByUser(Employee emp) {
        Query q = entityManager.createQuery("Select a from Activity a where a.owner.id = :id order by a.start");
        q.setParameter("id", emp.getId());

        return q.getResultList();
    }

    public void removeById(Long actId) {
        Query q = entityManager.createQuery("Delete from Activity a where id = :id").setParameter("id", actId);
        q.executeUpdate();
    }

    public List<Activity> findAllByWeekly(WeeklySheet currentWeekly) {
        Query q = entityManager.createQuery("Select a from Activity a where a.weeklySheet.id = :id").setParameter("id", currentWeekly.getId());
        List<Activity> results = q.getResultList();

        return results;
    }

    public List<Activity> findAllByWeeklyId(Long weeklyId) {
        Query q = entityManager.createQuery("Select a from Activity a where a.weeklySheet.id = :id").setParameter("id", weeklyId);
        List<Activity> results = q.getResultList();

        return results;
    }
}
