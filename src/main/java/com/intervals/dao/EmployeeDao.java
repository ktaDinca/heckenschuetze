package com.intervals.dao;

import com.intervals.model.Department;
import com.intervals.model.Employee;
import com.intervals.model.JobType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class EmployeeDao extends BaseDao {

	public Employee findEmployeeByUsername(String username) {
		Query query = entityManager.createQuery("select e from Employee e where e.username = :username");
		query.setParameter("username", username);
		
		if (query.getResultList() != null && query.getResultList().size() > 0) {
			return (Employee) query.getResultList().get(0);
		}
		return null;
	}
	
	public List<Employee> loadAll() {
		return this.entityManager.createQuery("Select e from Employee e order by e.username").getResultList();
	}

	public Employee findEmployeeByFirstAndLastName(String name) {
		Query query = entityManager.createQuery("select e from Employee e where e.firstName ||' '||e.lastName = :name");
		query.setParameter("name", name);
		
		List<Employee> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public void removeEmpFromDepartment(Long id) {
        entityManager.getTransaction().begin();
		Query q = entityManager.createQuery("Delete from Employee e where e.department.id = :id");
		q.setParameter("id", id);
		q.executeUpdate();
        entityManager.getTransaction().commit();
	}

	public List<Employee> findAllEmployeesFromDepartment(Department d) {
		Query q = entityManager.createQuery("Select e from Employee e where e.department.id = :deptno").setParameter("deptno", d.getId());
		return q.getResultList();
	}

    public void save(Employee e) {
        if (e == null) {
            return;
        }
        if (e.getId() != null) {
            entityManager.merge(e);
        }
        else {
            entityManager.persist(e);
        }

    }

    public Employee findEmployeeById(Long emp_id) {
        Query q = entityManager.createQuery("Select e from Employee e where e.id = :id");
        q.setParameter("id", emp_id);

        List<Employee> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public List<Employee> loadAllEmployeesByJob(JobType job) {
        Query q = entityManager.createQuery("Select e from Employee e where e.job = :job");
        q.setParameter("job", job);
        List<Employee> results = q.getResultList();
        return results;
    }

    public void remove(Long empid) {
        Query q = entityManager.createQuery("Delete from Employee where id = :id");
        q.setParameter("id", empid);
        q.executeUpdate();
    }
}
