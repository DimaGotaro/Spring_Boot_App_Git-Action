package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/applicationTest.properties")
class UserServTest {

    @Autowired
    private UserServ userServ;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private Model model;

    @MockBean
    private Map<String, Object> map;

    @Test
    void all_u() {

        String s = userServ.all_u(model);

        Assertions.assertEquals("user", s);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(ArgumentMatchers.any(String.class), (ArgumentMatchers.any(List.class)));
        Mockito.verify(userRepo, Mockito.times(1))
                .findAllByIdIsNotNullOrderByIdAsc();
    }

    @Test
    void update() {
        User id = new User();

        String s = userServ.update(id, map);

        Assertions.assertEquals("updateUser", s);
        Mockito.verify(map, Mockito.times(1))
                .put(ArgumentMatchers.any(String.class), (ArgumentMatchers.any(Role[].class)));
        Mockito.verify(map, Mockito.times(1))
                .put(ArgumentMatchers.any(String.class), (ArgumentMatchers.any(User.class)));
    }

    @Test
    void updateP() {
        User id = new User();
        User user = new User();
        user.setUsername("Dio");
        user.setPassword("blood");
        user.setRoles(Collections.singleton(Role.USER));

        String s = userServ.updateP(id, user);

        Assertions.assertEquals("redirect:/user", s);
        Mockito.verify(userRepo, Mockito.times(1)).save(id);
    }

    @Test
    void updateP2() {
        User id = null;
        User user = new User();
        user.setRoles(new TreeSet<>());

        String s = userServ.updateP(id, user);

        Assertions.assertEquals("redirect:/user/{id}", s);
        Mockito.verify(userRepo, Mockito.times(0)).save(id);
    }

    @Test
    void delete() {
        User user = new User();
        Message message = new Message();
        user.getMessage().add(message);
        message.setAuthor(user);

        String s = userServ.delete(user);

        Assertions.assertEquals("redirect:/user", s);
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }

    @Test
    void delete2() {
        User user = new User();

        String s = userServ.delete(user);

        Assertions.assertEquals("redirect:/user", s);
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }
}