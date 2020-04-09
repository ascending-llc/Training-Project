package com.ascending.training.service;


import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.User;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class JWTServiceTest {
    @Autowired
    private JWTService jwtService;

    @Test
    public void generateTokenTest(){
        User u = new User();
        u.setId(1L);
        u.setName("ryohang");
        String token = jwtService.generateToken(u);
//        assertion
        assertNotNull(token);
        String[] tArray = token.split("\\.");
        assertEquals(tArray.length,3);
    }

    @Test
    public void decryptJwtTokenTest(){
        User u = new User();
        u.setId(1L);
        u.setName("ryohang");
        String token = jwtService.generateToken(u);
        Claims c = jwtService.decryptJwtToken(token);
        String username = c.getSubject();
        assertEquals(u.getName(),username);
    }
}
