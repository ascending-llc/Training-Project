/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.controller;

import com.ascending.training.model.Employee;
import com.ascending.training.service.EmployeeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/ems", "/employees"})
public class EmployeeController {
    @Autowired private Logger logger;
    @Autowired private EmployeeService employeeService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }


//    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Employee getEmployee(@PathVariable String name) {
//        return employeeService.getEmployeeByName(name);
//    }

    @RequestMapping(value = "/{deptName}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Employee creatEmployee(@RequestBody Employee employee, @PathVariable String deptName) {
        logger.debug(String.format("Department name: %s, employee: %s", deptName, employee.toString()));
        String msg = "The employee was created.";
        Employee em = employeeService.save(employee, deptName);

        if (em!=null) msg = "The employee was not created.";

        return em;
    }
}