package com.intervals.dao;

import com.intervals.model.AbstractEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 05/Mar/2014
 */
@Repository
@Transactional
public class BaseDao {

    @PersistenceContext
    protected EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void saveOrUpdate(AbstractEntity e) {
        if (e == null) {
            throw new NullPointerException("Trying to persist a null Abstract Object");
        }
        else {
            if (e.getId() == null) {
                entityManager.persist(e);
            }
            else {
                entityManager.merge(e);
            }
        }
    }
}
