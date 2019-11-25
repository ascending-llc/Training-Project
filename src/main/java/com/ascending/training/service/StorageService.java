package com.ascending.training.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class StorageService {
    private String bucket = "car-service-dev";
    @Autowired
    private AmazonS3 s3Client;
    public void uploadObject(File f){
        if(f!=null){
            s3Client.putObject(bucket,f.getName(),f);
        }

    }

    public void getObject(String key){

    }
}
