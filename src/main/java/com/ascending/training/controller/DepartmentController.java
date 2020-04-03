/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.controller;

import com.ascending.training.model.Department;
import com.ascending.training.service.DepartmentService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/departments", "/depts"})
public class DepartmentController {
    @Autowired
    private Logger logger;
    @Autowired
    private DepartmentService departmentService;

    // /departments GET   /dep
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE})
    public List<Department> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return departments;
    }

    ///departments/with-children  GET
    @RequestMapping(value = "/with-children", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE})
    public List<Department> getDepartmentsWithChildren() {
        List<Department> departments = departmentService.getDepartmentsEager();
        return departments;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Department updateDepartmentName(@PathVariable("id") Long Id, @RequestParam("name") String name) {
        logger.info("pass in variable id: "+Id.toString()+"name: "+name);
        Department d = departmentService.getBy(Id);
        d.setName(name);
        d = departmentService.update(d);
        return d;
    }

    //{prefix}/department?name=xxx
    @RequestMapping(value="",method=RequestMethod.GET,params = {"name"})
    public Department getDepartment(@RequestParam("name") String name){
        logger.info("pass in variable name: "+name);
        return null;
    }
    //{prefix}/department?description=xxx
    @RequestMapping(value="",method=RequestMethod.GET,params = {"description"})
    public Department getDepartmentByDesc(@RequestParam("description") String description){
        logger.info("pass in variable description: "+description);
        return null;
    }
//    id=xadsfadsl12341234cvasdf
//    @RequestMapping(value = "/{deptName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Department getDepartment(@PathVariable String deptName) {
//        Department department = departmentService.getDepartmentByName(deptName);
//        return department;
//    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Department creatDepartment(@RequestBody Department department) {
        logger.debug("Department: " + department.toString());
        String msg = "The department was created.";
        Department dep = departmentService.save(department);

        if (dep == null) msg = "The department was not created.";

        return dep;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Department updateDepartment(@RequestBody Department department) {
        logger.debug("Department: " + department.toString());
        String msg = "The department was updated.";
        Department dep = departmentService.update(department);
        if (dep == null) msg = "The department was not update.";

        return dep;
    }

    @RequestMapping(value = "/{deptName}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteDepartment(@PathVariable String deptName) {
        logger.debug("Department name: " + deptName);
        String msg = "The department was deleted.";
        boolean isSuccess = departmentService.delete(deptName);
        if (!isSuccess) msg = "The department was not deleted.";

        return msg;
    }
}