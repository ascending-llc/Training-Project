package com.ascending.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.ascending.training.init.AppInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        storageService.uploadObject(null);
        verify(amazonS3,times(1)).putObject("car-service-dev",f.getName(),f);

    }

}
