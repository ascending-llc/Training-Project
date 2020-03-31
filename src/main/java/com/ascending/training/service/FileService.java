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
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class FileService {
    @Autowired
    private Logger logger;
    @Autowired
    private AmazonS3 amazonS3;

    /*
     * MultipartFile is a representation of an uploaded file received in a multipart request.
     * multipart request is the request that one or more different sets of data are combined in the request body
     *
     */
    public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        try {
            if (amazonS3.doesObjectExist(bucketName, file.getOriginalFilename())) {
                logger.info(String.format("The file '%s' exists in the bucket %s", file.getName(), bucketName));
                return null;
            }
//            amazonS3.putObject(bucketName,file.getName(),file);
            String uuid = UUID.randomUUID().toString();
            //ryohang.png-> ryohang.png+adafsdfalzcvdf bad
            //ryohang+2345badff.png good
            String originalFilename = file.getOriginalFilename();
            String newFileName = Files.getNameWithoutExtension(originalFilename)+uuid+Files.getFileExtension(originalFilename);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, newFileName, file.getInputStream(), objectMetadata);
            logger.info(String.format("The file name=%s, size=%d was uploaded to bucket %s", file.getName(), bucketName));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

        return getFileUrl(bucketName, file.getName());
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

    public boolean saveFile(MultipartFile multipartFile, String filePath) {
        boolean isSuccess = false;

        try {
            File directory = new File(filePath);
            if (!directory.exists()) directory.mkdir();
            Path path = Paths.get(filePath, multipartFile.getOriginalFilename());
            multipartFile.transferTo(path);
            isSuccess = true;
            logger.info(String.format("The file %s is saved in the foldr %s", multipartFile.getOriginalFilename(), filePath));
        }
        catch(Exception e) {
            logger.error(e.getMessage());
        }

        return isSuccess;
    }
}
