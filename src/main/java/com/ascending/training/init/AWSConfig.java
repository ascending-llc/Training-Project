package com.ascending.training.init;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.ascending.training.service.File2Service;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile("dev")
public class AWSConfig {
    //    @Bean
//    public DepartmentService getDepartmentService(){
//        DepartmentService ds = new DepartmentService();
//        ds.setPropert (xxx);
//        ds.setPropert(yyy);
//        return ds;
//    }

//    @Bean
//    public FileService getFile2Service(){
//        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
//        FileService file2Service = new FileService(s3Client);
//        file2Service.setBucketName("car-service-dev");
//        return file2Service;
//    }
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getAmazonS3() {
        return  AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonSQS getAmazonSQS() {
        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
//                .withRegion(Regions.US_EAST_1)
                .build();
    }

}
