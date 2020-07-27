/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Role;
import com.ascending.training.model.User;

public interface UserDao {
    boolean save(User user);
    User getUserByEmail(String email);
    User getUserById(Long Id);
    User getUserByCredentials(String email, String password) throws Exception;
    User addRole(User user, Role role);
    void delete(User u);
}
