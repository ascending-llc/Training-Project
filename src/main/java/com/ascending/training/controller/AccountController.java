/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.controller;

import com.ascending.training.model.Account;
import com.ascending.training.model.Employee;
import com.ascending.training.service.AccountService;
import com.ascending.training.service.EmployeeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = {"/accounts"})
public class AccountController {
    @Autowired private Logger logger;
    @Autowired private AccountService accountService;
    @Autowired private EmployeeService employeeService;

    //@GetMapping(value = "", produces = "application/json")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(value = "/{employeeName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<Account> getAccount(@PathVariable String employeeName) {
        Employee employee = employeeService.getEmployeeByName(employeeName);
        if (employee != null) return employee.getAccounts();
        return null;
    }

    //@PostMapping(value = "/{employeeName}", consumes = "application/json")
    @RequestMapping(value = "/{employeeName}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String creatAccount(@PathVariable String employeeName, @RequestBody Account account) {
        logger.debug(String.format("Employee name: %s, account: %s", employeeName, account.toString()));
        String msg = "The account was created.";
        Account act = accountService.save(account, employeeName);
        if (act!=null) logger.error("The account was not created.");
        return msg;
    }
}