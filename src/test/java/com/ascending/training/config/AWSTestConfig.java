package com.ascending.training.config;

import com.amazonaws.services.s3.AmazonS3;
//import com.ascending.training.service.File2Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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

}
