/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;


import com.ascending.training.model.Role;
import com.ascending.training.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserDaoTest {
    private Logger logger=LoggerFactory.getLogger(getClass());
    private UserDao userDao;
    private RoleDao roleDao;
    private String email;
    private List<Role> roles = new ArrayList();
    private User user;

    @Before
    public void init() {
        user=new User();
        userDao = new UserDaoImpl();
        roleDao = new RoleDaoImpl();
        email = "dwang@ascending.com";
        roles.add(roleDao.getRoleByName("Manager"));
        roles.add(roleDao.getRoleByName("User"));
    }

    @After
    public void tearDown() {
        user.setRoles(null);
        userDao.save(user);
        userDao.delete(user);
    }

    @Test
    public void getUserByEmail() {
        user = userDao.getUserByEmail(email);
        assertNotNull(user);
        logger.debug(user.toString());
    }

    @Ignore
    @Test
    public void createUser() {
        User user = new User();
        user.setRoles(roles);
        user.setName("jfang");
        user.setFirstName("John");
        user.setLastName("Fang");
        user.setEmail("jfang@ascending.com");
        user.setPassword("jfang123!@#$");
        boolean result = userDao.save(user);
        Assert.assertTrue(result);
    }

    @Test
    public void encryptPassword() {
        logger.debug("Hashed Password: " + DigestUtils.md5Hex("123456789"));
    }


    @Test
    public void addRoleTest(){
//        User user = new User();
        user.setRoles(roles);
        user.setName("jfang");
        user.setFirstName("John");
        user.setLastName("Fang");
        user.setEmail("jfang@ascending.com");
        user.setPassword("jfang123!@#$");
        userDao.save(user);
        assertTrue(user.getId()>0);
        User result = userDao.getUserById(user.getId());
        Assert.assertEquals(result.getRoles().size(),2);
        roles.add(roleDao.getRoleByName("Admin"));
        user.setRoles(roles);
        userDao.save(user);
        result = userDao.getUserById(user.getId());
        Assert.assertEquals(result.getRoles().size(),3);
    }
}