package com.intervals.service.impl;

import com.intervals.dao.NotificationDao;
import com.intervals.model.Notification;
import com.intervals.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 28/Apr/2014
 */
@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    NotificationDao notificationDao;

    @Override
    public void save(Notification notif) {
        notificationDao.saveOrUpdate(notif);
    }
}
