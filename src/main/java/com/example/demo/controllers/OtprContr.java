package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.other.StatM;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.OtprServ;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/o")
public class OtprContr {

    private OtprServ otprServ;

    public OtprContr(OtprServ otprServ) {
        this.otprServ = otprServ;
    }

    @GetMapping("/otpr")
    public String otpr(Model model,
                       @RequestParam(defaultValue = "", name = "res") String res,
                       @AuthenticationPrincipal User user) {

        return otprServ.otpr(model, res, user);
    }

    @PatchMapping("/otpr")
    public String otpr2(@RequestParam(defaultValue = "0") String per,
                        @RequestParam Long[] usr,
                        @AuthenticationPrincipal User user,
                        Model model) {

        return otprServ.otpr2(per, usr, user, model);
    }
}
