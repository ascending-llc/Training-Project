/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.ascending.training.model.Employee;
import com.ascending.training.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public Employee getBy(Long id){
        return employeeDao.getBy(id);
    }

    public Employee save(Employee employee, String deptName) {
        return employeeDao.save(employee, deptName);
    }

    public Employee update(Employee employee) {
        return employeeDao.update(employee);
    }

    public List<Employee> getEmployees() {
        return employeeDao.getEmployees();
    }

    public Employee getEmployeeByName(String name) {
        return employeeDao.getEmployeeByName(name);
    }
}
