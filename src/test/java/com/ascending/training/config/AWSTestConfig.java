package com.ascending.training.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
//import com.ascending.training.service.File2Service;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("unit")
public class AWSTestConfig {

//    @Bean
//    public File2Service getFile2Service(){
//        AmazonS3 s3Client = mock(AmazonS3.class);
//        File2Service file2Service = new File2Service(s3Client);
//        file2Service.setBucketName("car-service-unit");
//        return file2Service;
//    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getAmazonS3() {
        return  mock(AmazonS3.class);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonSQS getAmazonSQS() {
        return mock(AmazonSQS.class);
    }

}
