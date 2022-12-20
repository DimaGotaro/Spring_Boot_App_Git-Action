package com.example.demo.controllers;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.MessRepos;
import com.example.demo.repository.MessRepos2;
import com.example.demo.service.MessServ;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/mess")
public class MessContr {

private MessServ messServ;

    public MessContr(MessServ messServ) {
        this.messServ = messServ;
    }

    @GetMapping("/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         Map<String, Object> map) {

        return messServ.update(id, map);
    }

    @PatchMapping("/{id}")
    public String updateP(@PathVariable(name = "id") Message message1,
                          @ModelAttribute(name = "obj") Message message) {

        return messServ.updateP(message1, message);
    }

    @DeleteMapping("/d")
    public String delete(@RequestParam(name = "id") Message message) {

        return messServ.delete(message);
    }
}
