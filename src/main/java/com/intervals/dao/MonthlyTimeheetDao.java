package com.intervals.dao;

import com.intervals.model.Employee;
import com.intervals.model.MonthlyTimesheet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class MonthlyTimeheetDao extends BaseDao {

	public MonthlyTimesheet findByDateAndUser(Date as, Employee e) {
		Query q = entityManager.createQuery("Select m from MonthlyTimesheet m where m.date = :date and m.owner.id = :id");
		q.setParameter("date", as);
		q.setParameter("id", e.getId());

		List<MonthlyTimesheet> results = q.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public List<MonthlyTimesheet> findByUser(Employee e) {
		Query q = entityManager.createQuery("Select m from MonthlyTimesheet m where m.owner.id = :id").setParameter("id", e.getId());
		return q.getResultList();
	}

}
