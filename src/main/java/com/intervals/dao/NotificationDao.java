package com.intervals.dao;

import com.intervals.model.Employee;
import com.intervals.model.Notification;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */
@Repository
public class NotificationDao extends BaseDao {

    public List<Notification> findUnseenNoficationsForEmployee(Employee emp) {
        if (emp == null || emp.getId() == null) {
            return null;
        }

        Query q = entityManager.createQuery("Select n from Notification n where n.reviewingManager.id = :id and n.isSeen = false and n.type = 'EMP2MGR'");
        q.setParameter("id", emp.getId());

        return q.getResultList();
    }

    public Notification findNotificationById(Long notificationId) {
        Query q = entityManager.createQuery("Select n from Notification n where n.id = :id");
        q.setParameter("id", notificationId);

        List<Notification> results = q.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }
}
