package com.airvals.dao;

import com.airvals.model.Plane;
import com.intervals.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 13/Jun/2014
 */
@Repository
public class PlaneDao extends BaseDao {

    public List<Plane> loadAll() {
        Query q = entityManager.createQuery("Select p from Plane p order by p.name");
        return q.getResultList();
    }

    public Plane findPlaneById(Long id) {
        Query q = entityManager.createQuery("Select p from Plane p where p.id = :id");
        q.setParameter("id", id);

        List<Plane> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
