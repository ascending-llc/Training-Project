/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Department;

import java.util.List;

public interface DepartmentDao {
    Department save(Department department);
    Department update(Department department);
    boolean delete(String deptName);
    boolean delete(Department dep);
    List<Department> getDepartments();
    List<Department> getDepartmentsWithChildren();
    Department getDepartmentEagerBy(Long id);
    Department getDepartmentBy(Long id);
    Department getDepartmentByName(String deptName);
    Department getDepartmentAndEmployeesBy(String deptName);
    List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName);
}
