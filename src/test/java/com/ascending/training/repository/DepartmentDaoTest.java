/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Department;
import com.ascending.training.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

//import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class DepartmentDaoTest {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private EmployeeDao employeeDao;
//    @Autowired
//    private Logger logger;
    private Employee e1;
    private Employee e2;
    private Department d1;
    private String depString="HR1";

    @Before
    public void init() {
        //logic 1 save record in one side
//        departmentDao = new DepartmentDaoImpl();
//        employeeDao = new EmployeeDaoImpl();
        d1 = new Department();
        d1.setName(depString);
        d1.setDescription("random description");
        d1.setLocation("US");
        d1=departmentDao.save(d1);

        // d1.setEmployee([feixiong,xiaolu])
        //d1.setEmployee([ryo])
        //d1.setEmployees();
        //logic 2 save record in many side
        e1 = new Employee();
        e1.setName("zhang3");
        e1.setAddress("us");
//        //update employees set department_id=1 where employee.name='zhang3';
//        //e1.setDepartment_id(d1.getId);
        e1.setDepartment(d1);
        employeeDao.save(e1);
        e2 = new Employee();
        e2.setName("li4");
        e2.setDepartment(d1);
        employeeDao.save(e2);
    }
    @After
    public void tearDown() {
        //logic 1 delete record in many side
        employeeDao.delete(e1);
        employeeDao.delete(e2);
        //logic 2 delete record in one side
        departmentDao.delete(d1);

    }
//
//    @Test
//    public void getDepartmentAndEmployeesByTest(){
//        Department result= departmentDao.getDepartmentAndEmployeesBy(depString);
////        assertTrue(departments.size()>0);
////        Department result = departments.get(0);
//        assertEquals(result.getName(),depString);
//        Set<Employee> employeeSet = result.getEmployees();
//        assertEquals(employeeSet.size(),2);
//    }
    @Test
    public void simulateLazyLoadTest() throws JsonProcessingException {
        List<Department> departments = departmentDao.getDepartments();
        ObjectMapper mapper = new ObjectMapper();
//        Hibernate5Module hibernate5Module = new Hibernate5Module();
//        mapper.registerModule(hibernate5Module);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(departments));
//        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(mapper);
//        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
//        messageConverter.write(departments,MediaType.APPLICATION_JSON, mockHttpOutputMessage);
//        System.out.println(mockHttpOutputMessage.getBody());
    }

    @Test
    public void getDepartmentsTest() throws IOException {
        List<Department> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 1;

//        departments.forEach(dept -> System.out.println(dept));
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

    @Test
    public void getDepartmentEagerByTest(){
        Department department = departmentDao.getDepartmentEagerBy(d1.getId());
        assertNotNull(department);
        assertEquals(department.getName(),d1.getName());
        assertTrue(department.getEmployees().size()>0);
    }

    @Test(expected = HibernateException.class)
    public void getDepartmentByTest(){
        Department department = departmentDao.getBy(d1.getId());
        assertNotNull(department);
        assertEquals(department.getName(),d1.getName());
        System.out.println(department.getEmployees());
    }

    @Test
    public void getDepartmentByNameTest() {
        String deptName = "HR";
        Department department = departmentDao.getDepartmentByName(deptName);

//        logger.info(department.toString());
//        logger.info(department.getEmployees().toString());
//        logger.info(department.getEmployees().stream().findFirst().get().getAccounts().toString());

        Assert.assertEquals(deptName, department.getName());
    }

    @Test
    public void updateTest() {
        String deptName = "R&D";
        String location = "11126 Fairhaven Court, Fairfax, VA";
        Department department = departmentDao.getDepartmentByName(deptName);
        department.setLocation(location);
        departmentDao.update(department);
        department = departmentDao.getDepartmentByName(deptName);
        Assert.assertEquals(location, department.getLocation());
    }

    @Test
    public void getDepartmentAndEmployeesAndAccountsTest() {
        String deptName = "R&D";
        List<Object[]> resultList = departmentDao.getDepartmentAndEmployeesAndAccounts(deptName);
        Assert.assertEquals(2, resultList.size());
    }
}