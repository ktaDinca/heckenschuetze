package com.intervals.service;

import com.intervals.model.Employee;
import com.intervals.model.Notification;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */
public interface NotificationService {

    public void save(Notification notif);

    List<Notification> findUnseenNotifications(Employee emp);

    Notification findNotificationById(Long notificationId);

}
