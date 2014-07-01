package com.airvals.service.impl;

import com.airvals.dao.PersonDao;
import com.airvals.model.Person;
import com.airvals.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Jun/2014
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    public void saveOrUpdate(Person p) {
        if (p == null) {
            return;
        }
        personDao.saveOrUpdate(p);
    }
}
