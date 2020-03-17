/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.jdbc;

import com.ascending.training.model.Department;
import com.ascending.training.model.DepartmentJDBC;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//import static org.junit.Assert.assertEquals;

public class DepartmentJDBCDaoTest {
    private DepartmentJDBCDao departmentDao;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void init() {
        departmentDao = new DepartmentJDBCDao();
    }
//    @After
//    public void teardown(){
//
//    };

    @Test
    public void getDepartmentsTest() {
        List<DepartmentJDBC> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 4;
//        logger.debug("Connecting to database...");

//        for (Department department : departments) {
//            System.out.println(department);
//        }

        Assert.assertEquals(expectedNumOfDept, departments.size());
    }
}
