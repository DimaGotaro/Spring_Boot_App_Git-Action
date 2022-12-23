package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@TestPropertySource("/applicationTest.properties")
class RegisServTest {

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private RegisServ regisServ;

    @Test
    public void reg1() {
        User user = new User();
        Map<String, Object> map = new HashMap<>();

        String s = regisServ.regAdd(user, map);

        Assertions.assertEquals("redirect:/login", s);
        Assertions.assertNotNull(user.getMoney());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void reg2() {
        User user = new User();
        user.setUsername("Dima2");
        Map<String, Object> map = new HashMap<>();

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("Dima2");

        String s = regisServ.regAdd(user, map);

        Assertions.assertEquals("redirect:/regis", s);
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}






