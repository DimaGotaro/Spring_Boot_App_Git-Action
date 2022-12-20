package com.example.demo.controllers;

import com.example.demo.entity.Message;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserServ;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserContr {

    private UserServ userServ;

    public UserContr(UserServ userServ) {
        this.userServ = userServ;
    }

    @GetMapping
    public String all_u(Model model) {

        return userServ.all_u(model);
    }

    @GetMapping("/{id}")
    public String update(@PathVariable User id,
                         Map<String, Object> map) {

        return userServ.update(id, map);
    }

    @PatchMapping("/{id}")
    public String updateP(@PathVariable User id,
                          @ModelAttribute(name = "objU") User user) {

        return userServ.updateP(id, user);
    }

    @DeleteMapping("/d")
    public String delete(@RequestParam(name = "id") User user) {

        return userServ.delete(user);
    }
}
