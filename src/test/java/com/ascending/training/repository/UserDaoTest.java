/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;


import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Role;
import com.ascending.training.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class UserDaoTest {
    @Autowired
    private Logger logger;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    private String email;
    private List<Role> roles = new ArrayList();
    private User user;

    @Before
    public void init() {
        email = "dwang@ascending.com";
        roles.add(roleDao.getRoleByName("Manager"));
        roles.add(roleDao.getRoleByName("user"));
    }

    @After
    public void tearDown(){
        userDao.delete(user);
    }

    @Test
    public void getUserByEmail() {
        User user = userDao.getUserByEmail(email);
        Assert.assertNotNull(user);
        logger.debug(user.toString());
    }

    @Test
    public void getUserWithRoleTest() {
        user = new User();
        user.setRoles(roles);
        user.setName("jfang1552");
        user.setFirstName("John");
        user.setLastName("Fang");
        user.setEmail("jfang@ascending.com1552");
        user.setPassword("jfang123!@#$");
        user = userDao.save(user);
        User result = userDao.getUserByEmail(user.getEmail());
        assertEquals(result.getRoles().size(),roles.size());
    }


    @Test
    public void createUser() {
        user = new User();
        user.setRoles(roles);
        user.setName("jfang1234");
        user.setFirstName("John");
        user.setLastName("Fang");
        user.setEmail("jfang@ascending.com1234");
        user.setPassword("jfang123!@#$");
        user = userDao.save(user);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void encryptPassword() {
        logger.debug("Hashed Password: " + DigestUtils.md5Hex("123456789"));
    }
}