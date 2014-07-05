package com.airvals.service.impl;

import com.airvals.dao.InteractionDao;
import com.airvals.model.Interaction;
import com.airvals.model.User;
import com.airvals.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 04/Iul/2014
 */
@Service
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private InteractionDao interactionDao;

    @Override
    public void save(Interaction interaction) {
        interactionDao.saveOrUpdate(interaction);
    }

    @Override
    public List<Interaction> findLegalInteractionsByUser(User user) {
        if (user == null) {
            return null;
        }

        return interactionDao.findLegalByUser(user);
    }
}
