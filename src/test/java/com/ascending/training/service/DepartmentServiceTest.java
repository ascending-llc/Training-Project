/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Department;
import com.ascending.training.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class DepartmentServiceTest {
    private Logger logger=LoggerFactory.getLogger(getClass());
    @Autowired private DepartmentService departmentService;
    @Autowired private EmployeeService employeeService;

    private Employee e1;
    private Employee e2;
    private Department d1;
    private String depString="HR1";

    @Before
    public void init() {
        //You can not use new to creat the departmentService object directly,
        //as the DI is used to inject logger in DepartmentService, otherwise,
        //logger can not be injected, then it will through NullPointException
        //so, you have to use @Autowired inject the object departmentService
        //departmentService = new DepartmentService();
        //logic 1 save record in one side
        d1 = new Department();
        d1.setName(depString);
        d1.setDescription("random description");
        d1.setLocation("US");
        d1=departmentService.save(d1);
        e1 = new Employee();
        e1.setName("zhang3");
        e1.setAddress("us");
        e1.setDepartment(d1);
//        employeeService.save(e1);
        e2 = new Employee();
        e2.setName("li4");
        e2.setDepartment(d1);
//        employeeService.save(e2);
    }

    @Test
    public void getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        int expectedNumOfDept = 5;

        departments.forEach(dept -> System.out.println(dept));
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

    @Test
    public void getDepartmentByName() {
        String deptName = "R&D";
        Department department = departmentService.getDepartmentByName(deptName);

        logger.info(department.toString());
        logger.info(department.getEmployees().toString());
        logger.info(department.getEmployees().stream().findFirst().get().getAccounts().toString());

        Assert.assertEquals(deptName, department.getName());
    }

    @Test
    public void updateDepartmentLocation() {
        String deptName = "R&D";
        String location = "11126 Fairhaven Court, Fairfax, VA";
        Department department = departmentService.getDepartmentByName(deptName);
        department.setLocation(location);
        departmentService.update(department);
        department = departmentService.getDepartmentByName(deptName);
        Assert.assertEquals(location, department.getLocation());
    }

    @Test
    public void getDepartmentAndEmployeesTest() {
        String deptName = "R&D";
        Department department = departmentService.getDepartmentAndEmployees(deptName);
        Assert.assertEquals(department.getName(),deptName);
    }

    @Test
    public void getDepartmentAndEmployeesAndAccountsTest() {
        String deptName = "R&D";
        List<Object[]> resultList = departmentService.getDepartmentAndEmployeesAndAccounts(deptName);
        Assert.assertEquals(4, resultList.size());
    }

    @Test
    public void deleteDepartment() {
        //departmentService.delete("HR");
    }
}