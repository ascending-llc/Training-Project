package com.ascending.training.init;


import com.ascending.training.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Bean
    public SessionFactory getFactory() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        if (sf==null){
            logger.error("build hibernate session failure");
        }
        return sf;
    }
}
