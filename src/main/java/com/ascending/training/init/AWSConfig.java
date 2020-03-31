package com.ascending.training.init;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.ascending.training.service.File2Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("dev")
public class AWSConfig {
    //    @Bean
//    public DepartmentService getDepartmentService(){
//        DepartmentService ds = new DepartmentService();
//        ds.setPropert (xxx);
//        ds.setPropert(yyy);
//        return ds;
//    }

//    @Bean
//    public File2Service getFile2Service(){
//        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
//        File2Service file2Service = new File2Service(s3Client);
//        file2Service.setBucketName("car-service-dev");
//        return file2Service;
//    }

}
