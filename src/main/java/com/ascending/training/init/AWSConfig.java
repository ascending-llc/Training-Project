package com.ascending.training.init;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AWSConfig {
    @Bean
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

    @Bean
    public AmazonSQS getAmazonSQS() {
        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Logger logger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
