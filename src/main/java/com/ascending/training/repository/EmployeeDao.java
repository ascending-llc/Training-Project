/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Employee;

import java.util.List;

public interface EmployeeDao {
    Employee save(Employee employee, String deptName);
    Employee save(Employee employee);
    boolean delete(Employee employee);
//    Employee updateEmployeeAddress(String name, String address);
    Employee update(Employee employee);
    List<Employee> getEmployees();
    Employee getEmployeeByName(String name);
}
