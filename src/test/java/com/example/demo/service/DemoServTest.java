package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.MessRepos2;
import com.example.demo.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/applicationTest.properties")
class DemoServTest {

    @Autowired
    private DemoServ demoServ;
    @MockBean
    private Model model;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private MessRepos2 messRepos2;
    @MockBean
    MultipartFile file;

    @Test
    void popol() {
        User user = new User();
        user.setMoney(new BigDecimal("100"));

        String s = demoServ.popol("100.34", user, model);

        Assertions.assertEquals("redirect:/all", s);
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void popol2() {
        User user = new User();
        user.setMoney(new BigDecimal("100"));

        String s = demoServ.popol("0", user, model);

        Assertions.assertEquals("redirect:/all", s);
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void init() {
        String s = demoServ.init();

        Assertions.assertEquals("redirect:/login", s);
        Mockito.verify(userRepo, Mockito.times(1))
                .save(ArgumentMatchers.any(User.class));
    }

    @Test
    void all() {
        User user = new User();

        Map<String, Object> map = new HashMap<>();

        String s = demoServ.all(map, "tag", user, "res");

        Assertions.assertEquals("all", s);
        Mockito.verify(messRepos2, Mockito.times(1))
                .findByTag(ArgumentMatchers.any(String.class));
    }

    @Test
    void all2() {
        User user = new User();

        Map<String, Object> map = new HashMap<>();

        String s = demoServ.all(map, null, user, "res");

        Assertions.assertEquals("all", s);
        Mockito.verify(messRepos2, Mockito.times(0))
                .findByTag(ArgumentMatchers.any(String.class));
    }

    @Test
    void add() throws IOException {
        User user = new User();
        user.setId(1L);
        Message message = new Message();
        message.setText("text");
        message.setTag("tag");

        List<User> users = new ArrayList<>();
        users.add(user);
        Optional<User> optional = users.stream().findAny();

        Mockito.doReturn(optional)
                .when(userRepo)
                .findById(1L);
        Mockito.doReturn("file")
                .when(file)
                .getOriginalFilename();

        String s = demoServ.add(message, user, file);

        Assertions.assertEquals("redirect:/all", s);

        Mockito.verify(messRepos2, Mockito.times(1))
                .save(ArgumentMatchers.any(Message.class));
        Mockito.verify(userRepo, Mockito.times(1))
                .save(ArgumentMatchers.any(User.class));

        Mockito.verify(file, Mockito.times(1))
                .transferTo(ArgumentMatchers.any(File.class));
    }

    @Test
    void add2() throws IOException {
        User user = new User();
        user.setId(1L);
        Message message = new Message();
        message.setText("text");
        message.setTag("tag");

        List<User> users = new ArrayList<>();
        users.add(user);
        Optional<User> optional = users.stream().findAny();

        Mockito.doReturn(optional)
                .when(userRepo)
                .findById(1L);

        String s = demoServ.add(message, user, null);

        Assertions.assertEquals("redirect:/all", s);

        Mockito.verify(messRepos2, Mockito.times(1))
                .save(ArgumentMatchers.any(Message.class));
        Mockito.verify(userRepo, Mockito.times(1))
                .save(ArgumentMatchers.any(User.class));

        Mockito.verify(file, Mockito.times(0))
                .transferTo(ArgumentMatchers.any(File.class));
    }
}