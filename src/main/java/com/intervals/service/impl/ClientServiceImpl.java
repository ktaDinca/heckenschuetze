package com.intervals.service.impl;

import com.intervals.dao.ClientDao;
import com.intervals.model.Client;
import com.intervals.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 20/Apr/2014
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public List<Client> loadAll() {
        return clientDao.loadAll();
    }

    @Override
    public Client findById(Long clientId) {

        if (clientId == null) {
            return null;
        }
        return clientDao.findById(clientId);
    }

    @Override
    public void save(Client client) {
        clientDao.saveOrUpdate(client);
    }

    @Override
    public void remove(Long clientId) {
        clientDao.remove(clientId);
    }
}
