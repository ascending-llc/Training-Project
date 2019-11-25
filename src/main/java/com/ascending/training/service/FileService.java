package com.ascending.training.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.sqs.AmazonSQS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);
    /**
     * Dependency Injection method 2
     * @see com.ascending.training.service.FileService#MessageService(AmazonSQS,String)
     * @Autowired
     * private AmazonSQS amazonSQS;
     */

    /**
     * Dependency Injection method 1
     */
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {
        return uploadFile(bucketName,file);
    }


    public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        try {
            if (amazonS3.doesObjectExist(bucketName, file.getOriginalFilename())) {
                logger.info(String.format("The file '%s' exists in the bucket %s", file.getOriginalFilename(), bucketName));
                return null;
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), objectMetadata);
            logger.info(String.format("The file name=%s, size=%d was uploaded to bucket %s", file.getOriginalFilename(), file.getSize(), bucketName));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

        return getFileUrl(bucketName, file.getOriginalFilename());
    }

    public String getFilePresignedUrl(String fileName){
        return getFilePresignedUrl(bucketName,fileName);
    }

    public String getFilePresignedUrl(String bucketName, String fileName){
        LocalDateTime expiration = LocalDateTime.now().plusDays(1);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        generatePresignedUrlRequest.withMethod(HttpMethod.GET);
        generatePresignedUrlRequest.withExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)));

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    public String getFileUrl(String s3Key){
        return getFileUrl(bucketName,s3Key);
    }

    public String getFileUrl(String bucketName, String s3Key){
        return amazonS3.getUrl(bucketName,s3Key).toString();
    }

    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) amazonS3.createBucket(bucketName);
    }
}
