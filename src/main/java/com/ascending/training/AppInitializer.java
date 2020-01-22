/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/*
 * Maven skip test:
 * mvn -Dmaven.test.skip=true clean compile package
 *
 */

/* Define VM options
    -Ddatabase.driver=org.postgresql.Driver
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db
    -Ddatabase.user=admin
    -Ddatabase.password=Training123!
    -Dlogging.level.org.springframework=INFO
    -Dlogging.level.com.ascending=TRACE
    -Dserver.port=8080
    -Dsecret.key=Training123!@#
*/

/*  Run Spring boot application by mvn command line
    mvn spring-boot:run \
    -Dmaven.test.skip=true \
    -Ddatabase.driver=org.postgresql.Driver \
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect \
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db \
    -Ddatabase.user=admin \
    -Ddatabase.password=Training123! \
    -Dlogging.level.org.springframework=INFO \
    -Dlogging.level.com.ascending=TRACE \
    -Dserver.port=8080 \
    -Dsecret.key=Training123!
 */

@SpringBootApplication(scanBasePackages = {"com.ascending.training"})
@ServletComponentScan(basePackages = {"com.ascending.training.filter"})
public class AppInitializer extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AppInitializer.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AppInitializer.class);
    }

}