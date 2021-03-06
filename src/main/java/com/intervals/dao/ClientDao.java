package com.intervals.dao;


import com.intervals.model.Client;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class ClientDao extends BaseDao {

    public Client findByName(String name) {
        Query q = entityManager.createQuery("Select c from Client c where c.name = :name");
        q.setParameter("name", name);

        List<Client> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public List<Client> loadAll() {
        return this.entityManager.createQuery("Select e from Client e order by e.name").getResultList();
    }

    public Client findById(Long clientId) {
        Query q = entityManager.createQuery("Select c from Client c where c.id = :id").setParameter("id", clientId);

        List<Client> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public void remove(Long clientId) {
        Query q = entityManager.createQuery("Delete from Client where id = :id");
        q.setParameter("id", clientId);

        q.executeUpdate();
    }
}
