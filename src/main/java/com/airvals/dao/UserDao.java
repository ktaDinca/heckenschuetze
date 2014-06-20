package com.airvals.dao;

import com.airvals.model.User;
import com.intervals.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 10/Jun/2014
 */
@Repository
public class UserDao extends BaseDao {

    public User findUserByEmail(String email) {
        Query q = entityManager.createQuery("Select u from User u where u.email = :email");
        q.setParameter("email", email);

        List<User> results = q.getResultList();
        if(results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
