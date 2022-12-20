package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@Service
@Transactional
public class RegisServ {

    private UserRepo userRepo;

    public RegisServ(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String regAdd(User user, Map<String, Object> map) {

        User byUsername = userRepo.findByUsername(user.getUsername());
        if (byUsername != null) {
            map.put("messag", "Пользователь занят!");
            return "redirect:/regis";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setMoney(new BigDecimal("1111.11"));

        userRepo.save(user);

        return "redirect:/login";
    }
}
