/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.ascending.training.init.AppInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class FileServiceTest {
    @Autowired
    private Logger logger;
    @Autowired
    private FileService fileService;
    @Value("${aws.s3.bucket}")
    private String bucketName;
    private String fileName = "testFile.txt";
    private MultipartFile multipartFile;

    @Before
    public void setUp() throws IOException {
        logger.info(">>>>>>>>>> Start testing...");
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        multipartFile = new MockMultipartFile("file", fileName, "text/plain", is);

    }

    @After
    public void tearDown() {
        logger.info(">>>>>>>>>> End test");
    }

    @Test
    public void uploadFile() throws IOException{
        String fileUrl = fileService.uploadFile(bucketName, multipartFile);
        Assert.assertNotNull(fileUrl);
    }

//    @Test
//    public void saveFile() throws IOException, FileNotFoundException {
//        boolean isSuccess = fileService.saveFile(multipartFile, path);
//        Assert.assertTrue(isSuccess);
//    }
}

/*
 *   doReturn/when VS when/thenReturn:
 *      1. doReturn/when and when/thenReturn are same for mocked object. None of them call the actual method
 *      2. doReturn/when and when/thenReturn have different behaviour for spied object.
 *         doReturn/when - it will not call real method on spied object
 *         when/thenReturn - it will call real method on spied object
 */