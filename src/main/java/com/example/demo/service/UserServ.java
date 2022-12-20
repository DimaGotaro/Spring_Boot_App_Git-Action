package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Map;

@Service
@Transactional
public class UserServ {

    private UserRepo userRepo;

    public UserServ(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String all_u(Model model) {
        model.addAttribute("allf", userRepo.findAllByIdIsNotNullOrderByIdAsc());

        return "user";
    }

    public String update(User id, Map<String, Object> map) {
        Role[] rol = Role.values();

        map.put("rol", rol);
        map.put("objU", id);

        return "updateUser";
    }

    public String updateP(User id, User user) {
        if( id != null && user.getRoles().size() != 0) {
            id.setUsername(user.getUsername());
            id.setPassword(user.getPassword());
            id.setRoles(user.getRoles());
            userRepo.save(id);

            return "redirect:/user";
        }
        else {
            return "redirect:/user/{id}";
        }
    }

    public String delete(User user) {

        if (user.getMessage() != null) {
            for (Message message : user.getMessage()) {
                message.setAuthor(null);
            }
            user.getMessage().clear();
        }
        userRepo.delete(user);

        return "redirect:/user";
    }
}
