package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.RegisServ;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegisContr {
    private RegisServ regisServ;
    public RegisContr(RegisServ regisServ) {
        this.regisServ = regisServ;
    }

    @GetMapping("/regis")
    public String regis(@ModelAttribute(name = "usr") User user,
                        @RequestParam(defaultValue = "") String messag,
                        Model model) {
        model.addAttribute("messag", messag);

        return "regis";
    }

    @PostMapping("/regis")
    public String addN(@ModelAttribute(name = "usr") @Valid User user,
                       BindingResult bindingResult,
                       Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            return "/regis";
        }

        return regisServ.regAdd(user, map);
    }
}
