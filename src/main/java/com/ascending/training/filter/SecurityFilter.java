/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.filter;

import com.ascending.training.model.User;
import com.ascending.training.service.JWTService;
import com.ascending.training.service.UserService;
import com.ascending.training.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//
@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    private Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    private static String AUTH_URI = "/auth";
    @Autowired
    private JWTService jwtService;
//    private static String[] IGNOREURL={"/auth/*","/user"};
//
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (userService == null) {
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, request.getServletContext());
        }
        HttpServletRequest req = (HttpServletRequest)request;
//        HttpSession session = req.getSession();
//        session.setAttribute("appUserId",51234);
        int statusCode = authorization(req);
        if (statusCode == HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(request, response);
        else ((HttpServletResponse)response).sendError(statusCode);
    }

    public void destroy() {
        // TODO Auto-generated method stub
    }
//
    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();
        if (uri.equalsIgnoreCase(AUTH_URI)) return HttpServletResponse.SC_ACCEPTED;
//
        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", "");
            if (token == null || token.isEmpty()) return statusCode;
//
            Claims claims = jwtService.decryptJwtToken(token);
            //TODO pass username and check role
            if(claims.getId()!=null){
                User u = userService.getById(Long.valueOf(claims.getId()));
                if(u==null) return statusCode;
//                if(u==null)  statusCode = HttpServletResponse.SC_ACCEPTED;
            }
            String allowedResources = "/";
            switch(verb) {
                case "GET"    : allowedResources = (String)claims.get("allowedReadResources");   break;
                case "POST"   : allowedResources = (String)claims.get("allowedCreateResources"); break;
                case "PUT"    : allowedResources = (String)claims.get("allowedUpdateResources"); break;
                case "DELETE" : allowedResources = (String)claims.get("allowedDeleteResources"); break;
            }

            for (String s : allowedResources.split(",")) {
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }

            logger.debug(String.format("Verb: %s, allowed resources: %s", verb, allowedResources));
        }
        catch (Exception e) {
            logger.error("can't verify the token",e);
        }
        return statusCode;
    }

}