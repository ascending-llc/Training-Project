/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.jdbc;

import com.ascending.training.model.Department;
import org.junit.*;

import java.util.List;

public class DepartmentDaoTest {
    private DepartmentDao departmentDao;
    private Department d;

    @BeforeClass
    public static void classSetup(){
        System.out.println("classSetup");
    }

    @AfterClass
    public static void classTearDown(){
        System.out.println("classTearDown");
    }

    @Before
    public void init() {
        System.out.println("instanceSetup");
        departmentDao = new DepartmentDao();
        d = new Department();
        d.setDescription("xxxx");
        departmentDao.save(d);
    }

    @After
    public void tearDown(){
        System.out.println("instanceTearDown");
        departmentDao.delete(d.getId());
    }

    @Test
    public void getDepartmentsTest() {
        System.out.println("Test method 1");
        List<Department> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 5;

        for (Department department : departments) {
            System.out.println(department);
        }

        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

    @Test
    public void departmentByNameTest(){
        System.out.println("Test method 2");
    }
}
