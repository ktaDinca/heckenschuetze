package com.airvals.service;

import com.airvals.model.User;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 10/Jun/2014
 */
public interface UserService {

    public void saveOrUpdate (User user);

    User findUserByEmail(String email);
}
