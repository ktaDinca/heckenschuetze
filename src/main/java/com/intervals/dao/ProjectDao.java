package com.intervals.dao;

import com.intervals.model.Department;
import com.intervals.model.Employee;
import com.intervals.model.Project;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class ProjectDao extends BaseDao {

	public List<Project> loadAll() {
		return this.entityManager.createQuery("Select p from Project p order by p.name").getResultList();
	}

	public List<Project> findProjectsForEmployee(Employee e) {
		if (e == null || e.getId() == null || e.getDepartment() == null
				|| e.getDepartment().getId() == null) {
			return null;
		}

		String query = "Select p from Project p where p.department.id = "
				+ e.getDepartment().getId();
		return entityManager.createQuery(query).getResultList();
	}

	public Project findProjectByName(String name) {
		if (name == null || name.length() == 0) {
			return null;
		}

		Query q = entityManager
				.createQuery("Select p from Project p where p.name = :name");
		q.setParameter("name", name);
		List<Project> resultList = (List<Project>) q.getResultList();

		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}

	public List<Project> findProjectsForDepartment(Department department) {
		Query q = entityManager.createQuery(
				"Select p from Project p where p.department.id = :id order by p.name")
				.setParameter("id", department.getId());
		List<Project> results = (List<Project>) q.getResultList();
		return results;
	}

    public Project loadById(Long projectId) {
        Query q = entityManager.createQuery("Select p from Project p where p.id = :id");
        q.setParameter("id", projectId);

        List<Project> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }


    public void remove(Long projectId) {
        Query q = entityManager.createQuery("Delete from Project where id = :id");
        q.setParameter("id", projectId);

        q.executeUpdate();
    }
}
