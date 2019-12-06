package com.ascending.training.service;


import com.ascending.training.AppInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class MessageServiceTest {
    @Autowired
    private MessageService1 messageService1;

    @Test
    public void sendEventTest(){
        messageService1.sendEvent("asdfads");
        assertTrue(false);
    }
}
