package com.intervals.service;

import com.intervals.model.Client;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 20/Apr/2014
 */

public interface ClientService {
    List<Client> loadAll();

    Client findById(Long clientId);

    void save(Client client);

    void remove(Long clientId);

}
