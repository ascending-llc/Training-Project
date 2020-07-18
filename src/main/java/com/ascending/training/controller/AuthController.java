/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.controller;

import com.ascending.training.model.User;
import com.ascending.training.service.JWTService;
import com.ascending.training.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/auth"})
public class AuthController {
    @Autowired private Logger logger;
    @Autowired private UserService userService;
    @Autowired private JWTService jwtService;
    private String errorMsg = "The email or password is not correct.";
    private String tokenKeyWord = "Authorization";
    private String tokenType = "Bearer";

    //1.validate user exist in database and verify the password
    //2.generate JWToken
    //3.return token
//    @RequestMapping(value = "",method = RequestMethod.POST)
//    public String authentication(@RequestBody User user){
//        logger.debug("username is "+ user.getEmail()+ "password is "+ user.getPassword());
//        try {
//            User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
//            String token = jwtService.generateToken(u);
//            return token;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    //assignment {"token": "<token>"}
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> authenticate(@RequestBody User user) {
        //1. validate username/password
        //400 bad request
        //2. generate token
        //200 or 500
        String token;
        Map<String,String> result = new HashMap<>();
        try {
            logger.debug(user.toString());
            User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
            //u!=null
            if (u == null){
                result.put("msg",errorMsg);
                return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(result);
            }
            logger.debug(u.toString());
            token = jwtService.generateToken(u);
            result.put("token",token);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg == null) msg = "BAD REQUEST!";
            logger.error(msg);
            result.put("msg",msg);
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(result);
        }

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(result);
    }
}
