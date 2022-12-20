package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.List.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OtprServTest {

    @Autowired
    private OtprServ otprServ;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private Model model;

    @Test
    public void otpr() {

        User user = new User();
        user.setId(3L);

        List<User> asc = new ArrayList<>();
        asc.add(user);

        List<User> users = new ArrayList<>();
        users.add(user);
        Optional<User> optional = users.stream().findAny();

        Mockito.doReturn(optional)
                .when(userRepo)
                .findById(user.getId());

        Mockito.doReturn(asc)
                .when(userRepo)
                .findAllByIdIsNotNullOrderByIdAsc();

        String s = otprServ.otpr(model, "res", user);

        Assertions.assertEquals("otpr", s);
        Assertions.assertFalse(asc.contains(user));
        Mockito.verify(userRepo, Mockito.times(1)).findById(user.getId());
        Mockito.verify(userRepo, Mockito.times(1))
                .findAllByIdIsNotNullOrderByIdAsc();
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(ArgumentMatchers.any(String.class), (ArgumentMatchers.any(List.class)));
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(ArgumentMatchers.any(String.class), (ArgumentMatchers.any(String.class)));
    }

    @Test
    void otpr2() {
        String per = "134.78";
        User user = new User();
        user.setId(1L);
        user.setMoney(new BigDecimal("1111.11"));
        User user1 = new User();
        user1.setId(2L);
        user1.setMoney(new BigDecimal("1111.11"));
        User user2 = new User();
        user2.setId(3L);
        user2.setMoney(new BigDecimal("1111.11"));
        Long[] usr = {0L, user1.getId(), user2.getId()};

        List<User> users = new ArrayList<>();
        users.add(user1);
        Optional<User> optional = users.stream().findAny();

        Mockito.doReturn(optional)
                .when(userRepo)
                .findById(user1.getId());

        List<User> users2 = new ArrayList<>();
        users2.add(user2);
        Optional<User> optional2 = users2.stream().findAny();

        Mockito.doReturn(optional2)
                .when(userRepo)
                .findById(user2.getId());

        List<User> users1 = new ArrayList<>();
        users1.add(user);
        Optional<User> optional1 = users1.stream().findAny();

        Mockito.doReturn(optional1)
                .when(userRepo)
                .findById(user.getId());

        String s = otprServ.otpr2(per, usr, user, model);

        Assertions.assertEquals("redirect:/o/otpr", s);
        Assertions.assertEquals(new BigDecimal("841.55"), user.getM());
        Assertions.assertEquals(new BigDecimal("1245.89"), user1.getM());
        Assertions.assertEquals(new BigDecimal("1245.89"), user2.getM());
        Mockito.verify(userRepo, Mockito.times(1)).findById(user.getId());
        Mockito.verify(userRepo, Mockito.times(1)).findById(user1.getId());
        Mockito.verify(userRepo, Mockito.times(1)).findById(user2.getId());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(userRepo, Mockito.times(1)).save(user1);
        Mockito.verify(userRepo, Mockito.times(1)).save(user2);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(ArgumentMatchers.any(String.class), (ArgumentMatchers.any(String.class)));
    }

    @Test
    void otpr21() {
        String per = "1f4,78r4";
        User user = new User();
        User user1 = new User();
        User user2 = new User();
        Long[] usr = {0L};

        String s = otprServ.otpr2(per, usr, user, model);

        Assertions.assertEquals("redirect:/o/otpr", s);
        Mockito.verify(userRepo, Mockito.times(0)).findById(user.getId());
        Mockito.verify(userRepo, Mockito.times(0)).findById(user1.getId());
        Mockito.verify(userRepo, Mockito.times(0)).findById(user2.getId());
        Mockito.verify(userRepo, Mockito.times(0)).save(user);
        Mockito.verify(userRepo, Mockito.times(0)).save(user1);
        Mockito.verify(userRepo, Mockito.times(0)).save(user2);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(ArgumentMatchers.any(String.class), (ArgumentMatchers.any(String.class)));
    }

    @Test
    void otpr22() {
        String per = "1134.78";
        User user = new User();
        user.setId(1L);
        user.setMoney(new BigDecimal("1111.11"));
        User user1 = new User();
        user1.setId(2L);
        user1.setMoney(new BigDecimal("1111.11"));
        User user2 = new User();
        user2.setId(3L);
        user2.setMoney(new BigDecimal("1111.11"));
        Long[] usr = {0L, user1.getId(), user2.getId()};

        List<User> users = new ArrayList<>();
        users.add(user1);
        Optional<User> optional = users.stream().findAny().map(x -> x = null);

        Mockito.doReturn(optional)
                .when(userRepo)
                .findById(user1.getId());

        List<User> users2 = new ArrayList<>();
        users2.add(user2);
        Optional<User> optional2 = users2.stream().findAny().map(x -> x = null);

        Mockito.doReturn(optional2)
                .when(userRepo)
                .findById(user2.getId());

        List<User> users1 = new ArrayList<>();
        users1.add(user);
        Optional<User> optional1 = users1.stream().findAny().map(x -> x = null);

        Mockito.doReturn(optional1)
                .when(userRepo)
                .findById(user.getId());

        String s = otprServ.otpr2(per, usr, user, model);

        Assertions.assertEquals("redirect:/o/otpr", s);
        Mockito.verify(userRepo, Mockito.times(1)).findById(user.getId());
        Mockito.verify(userRepo, Mockito.times(1)).findById(user1.getId());
        Mockito.verify(userRepo, Mockito.times(1)).findById(user2.getId());
        Mockito.verify(userRepo, Mockito.times(0)).save(user);
        Mockito.verify(userRepo, Mockito.times(0)).save(user1);
        Mockito.verify(userRepo, Mockito.times(0)).save(user2);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(ArgumentMatchers.any(String.class), (ArgumentMatchers.any(String.class)));
    }
}