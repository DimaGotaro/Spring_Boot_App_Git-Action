package com.example.demo.service;

import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@TestPropertySource("/applicationTest.properties")
class RegisServ2Test {

    @Autowired
    private RegisServ regisServ;

    @Test
    public void reg21() {
        User user = new User();
        user.setUsername("Admin");
        Map<String, Object> map = new HashMap<>();

        String s = regisServ.regAdd(user, map);

        Assertions.assertEquals("redirect:/regis", s);
    }
}