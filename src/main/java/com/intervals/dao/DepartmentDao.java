package com.intervals.dao;


import com.intervals.model.Department;
import com.intervals.model.Division;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class DepartmentDao extends BaseDao {

	public List<Department> loadAll() {
		Query q = entityManager.createQuery("Select d from Department d order by d.name");
		return q.getResultList();
	}

	public Department findByName(String name) {
		Query q = entityManager
				.createQuery("Select d from Department d where d.name = :name");
		q.setParameter("name", name);

		List<Department> results = q.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public Department findByManager(Long id) {
		Query q = entityManager.createQuery("Select d from Department d where d.manager.id = :id");
		q.setParameter("id", id);
		List<Department> results = q.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public List<Department> loadAllFromDivision(Division d) {
		Query q = entityManager.createQuery("Select d from Department d where d.division.id = :id order by d.name");
		q.setParameter("id", d.getId());
		return q.getResultList();
	}

    public Department findDepartmentById(Long dept_id) {
        Query q = entityManager.createQuery("Select d from Department d where d.id = :id");
        q.setParameter("id", dept_id);

        List<Department> results = q.getResultList();

        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
