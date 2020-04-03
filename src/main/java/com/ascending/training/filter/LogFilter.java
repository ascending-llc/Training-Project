/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebFilter(filterName = "logFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class LogFilter implements Filter {
//    @Autowired private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final List<String> excludedWords = Arrays.asList("newPasswd", "confirmPasswd", "passwd", "password");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        if (logger == null) {
//            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, request.getServletContext());
//        }
        Instant startTime = Instant.now();
        HttpServletRequest req = (HttpServletRequest)request;
        String logInfo = logInfo(req);
        filterChain.doFilter(request, response);
        //todo change to ms
        logger.info(logInfo.replace("responseTime", String.valueOf(Instant.now().getEpochSecond() - startTime.getEpochSecond())));
    }

    public void destroy() {
        // TODO Auto-generated method stub
    }

    private boolean isIgnoredWord(String word, List<String> excludedWords) {
        for (String excludedWord : excludedWords) {
            if (word.toLowerCase().contains(excludedWord)) return true;
        }

        return false;
    }

    private String logInfo(HttpServletRequest req) {
        String formData = null;
        String httpMethod = req.getMethod();

        LocalDateTime startDateTime = LocalDateTime.now();
        String requestURL = req.getRequestURI();
        String userIP = req.getRemoteHost();
        String sessionID = req.getSession().getId();
        Enumeration<String> parameterNames = req.getParameterNames();
        List<String> parameters = new ArrayList();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (isIgnoredWord(paramName, excludedWords)) continue;

            String paramValues = Arrays.asList(req.getParameterValues(paramName)).toString();
            parameters.add(paramName + "=" + paramValues);
        }

        if (!parameters.isEmpty()) {
            formData = parameters.toString().replaceAll("^.|.$", "");
        }

        return  new StringBuilder("| ")
                .append(formatter.format(startDateTime)).append(" | ")
                .append(userIP).append(" | ")
                .append(httpMethod).append(" | ")
                .append(requestURL).append(" | ")
                .append(sessionID).append(" | ")
                .append("responseTime ms").append(" | ")
                .append(formData).toString();
    }

}