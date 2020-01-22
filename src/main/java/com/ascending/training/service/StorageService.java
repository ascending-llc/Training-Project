package com.ascending.training.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class StorageService {
    private String bucket = "car-service-dev";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AmazonS3 s3Client;
    public void uploadObject(File f){
        if(f!=null){
            s3Client.putObject(bucket,f.getName(),f);
        }

    }

    public void uploadObject(MultipartFile mf){
        String originalFileName = mf.getOriginalFilename();
        File f = new File(originalFileName);
        try {
            mf.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(f!=null){
            PutObjectResult por = s3Client.putObject(bucket,f.getName(),f);
            logger.info(por.getContentMd5());
        }

    }

    public void getObject(String key){

    }
}
