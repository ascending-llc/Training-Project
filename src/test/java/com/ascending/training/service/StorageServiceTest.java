package com.ascending.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ascending.training.AppInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class StorageServiceTest {
    @Autowired
    private StorageService storageService;
    @Autowired
    private AmazonS3 amazonS3;

    @Test
    public void putObjectTest(){
        File f = new File("xxxearafsdfasdf");
        storageService.uploadObject(f);
        verify(amazonS3,times(1)).putObject("car-service-dev",f.getName(),f);
//        verify(amazonS3).putObject("car-service-dev",f.getName(),f);

//        storageService.uploadObject(null);
//        verify(amazonS3,times(1)).putObject("car-service-dev",f.getName(),f);

    }

    @Test
    public void putObjectTest1(){
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.getOriginalFilename()).thenReturn("zhibo.jpg");
        when(amazonS3.putObject(anyString(),anyString(),any(File.class))).thenReturn(mock(PutObjectResult.class));
        storageService.uploadObject(mf);
    }

}
