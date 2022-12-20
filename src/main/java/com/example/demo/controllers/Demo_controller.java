package com.example.demo.controllers;

import com.example.demo.entity.Message;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.other.StatM;
import com.example.demo.repository.MessRepos2;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.DemoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class Demo_controller {

    @GetMapping
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name,
                        Model model) {
        model.addAttribute("name", name);

        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam(value = "name", defaultValue = "Gargantua") String name,
                        Map<String, Object> model) {
        model.put("name", name);

        return "hello";
    }

    @GetMapping("/403")
    public String error() {
        return "403";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/ws")
    public String lp() {
        return "redirect:/all";
    }

    private DemoServ demoServ;

    public Demo_controller(DemoServ demoServ) {
        this.demoServ = demoServ;
    }

    @GetMapping("/init")
    public String init() {

        return demoServ.init();
    }

    @PatchMapping("/popol")
    public String popol(@RequestParam(defaultValue = "0") String mon,
                        @RequestParam User id,
                        Model model) {

        return demoServ.popol(mon, id, model);
    }

    @GetMapping("/all")
    public String all(Map<String, Object> map,
                      @RequestParam(name = "fil", required = false) String tag,
                      @AuthenticationPrincipal User user,
                      @RequestParam(defaultValue = "", name = "res") String res) {

        return demoServ.all(map, tag, user, res);
    }

    @GetMapping("/new")
    public String newUsr (@ModelAttribute(name = "sob") Message message) {
        return "newMess";
    }

    @PostMapping("/all")
    public String add(@ModelAttribute(name = "sob") @Valid Message messageN,
                      BindingResult bindingResult,
                      @AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            return "newMess";
        }

        return demoServ.add(messageN, user, file);
    }
}