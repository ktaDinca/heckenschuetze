package com.airvals.dao;

import com.airvals.model.Interaction;
import com.airvals.model.User;
import com.intervals.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 04/Iul/2014
 */
@Repository
public class InteractionDao extends BaseDao {

    public List<Interaction> findLegalByUser(User user) {
        Query q = entityManager.createQuery("Select i from Interaction i where i.user.id = :id and i.action <> :action order by i.date desc");
        q.setParameter("action", "search");
        q.setParameter("id", user.getId());

        return q.getResultList();
    }
}
