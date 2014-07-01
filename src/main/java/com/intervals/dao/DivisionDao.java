package com.intervals.dao;

import com.intervals.model.Division;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class DivisionDao extends BaseDao {
	
	public List<Division> loadAll() {
		return this.entityManager.createQuery("Select d from Division d order by d.name").getResultList();
	}

	public Division findByName(String name) {
		Query q = entityManager.createQuery("Select d from Division d where d.name = :name");
		q.setParameter("name", name);

		List<Division> results = q.getResultList();

		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

    public Division findById(Long id) {
        Query q = entityManager.createQuery("Select d from Division d where d.id = :id");
        q.setParameter("id", id);

        List<Division> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public void remove(Long divid) {
        Query q = entityManager.createQuery("Delete from Division where id = :id").setParameter("id", divid);
        q.executeUpdate();
    }
}
