package com.airvals.service;

import com.airvals.model.Interaction;
import com.airvals.model.User;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 04/Iul/2014
 */
public interface InteractionService {

    public void save(Interaction interaction);

    List<Interaction> findLegalInteractionsByUser(User user);
}
