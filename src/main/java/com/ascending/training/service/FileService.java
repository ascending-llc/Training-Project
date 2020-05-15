/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service //new FileService(amazonS3);
public class FileService {
    @Autowired
    private Logger logger;
//    @Autowired
    private AmazonS3 amazonS3;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    //constructor DI
    public FileService(@Autowired AmazonS3 amazonS3){
        this.amazonS3=amazonS3;
    }

    //setter based DI
    //    public void setAmazonS3(@Autowired AmazonS3 amazonS3) {
    //        this.amazonS3 = amazonS3;
    //    }

    public String uploadFile(MultipartFile file) throws IOException {
        return uploadFile(bucketName,file);
    }

    /*
     * MultipartFile is a representation of an uploaded file received in a multipart request.
     * multipart request is the request that one or more different sets of data are combined in the request body
     *
     */
    public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        try {
            String uuid = UUID.randomUUID().toString();
//            //ryohang.png-> ryohang.png+adafsdfalzcvdf bad
//            //ryohang+2345badff.png good
            String originalFilename = file.getOriginalFilename();
            String newFileName = uuid+"."+Files.getFileExtension(originalFilename);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, newFileName, file.getInputStream(), objectMetadata);
            logger.info(String.format("The file name=%s was uploaded to bucket %s", newFileName, bucketName));
            //return getFileUrl(bucketName, file.getName());
            return newFileName;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public String getFileUrl(String fileName) {
        return getFileUrl(bucketName,fileName);
    }

    public String getFileUrl(String bucketName, String fileName) {
        LocalDateTime expiration = LocalDateTime.now().plusDays(1);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        generatePresignedUrlRequest.withMethod(HttpMethod.GET);
        generatePresignedUrlRequest.withExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)));

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) amazonS3.createBucket(bucketName);
    }

//    public boolean saveFile(MultipartFile multipartFile, String filePath) {
//        boolean isSuccess = false;
//
//        try {
//            File directory = new File(filePath);
//            if (!directory.exists()) directory.mkdir();
//            Path path = Paths.get(filePath, multipartFile.getOriginalFilename());
//            multipartFile.transferTo(path);
//            isSuccess = true;
//            logger.info(String.format("The file %s is saved in the foldr %s", multipartFile.getOriginalFilename(), filePath));
//        }
//        catch(Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        return isSuccess;
//    }
}
