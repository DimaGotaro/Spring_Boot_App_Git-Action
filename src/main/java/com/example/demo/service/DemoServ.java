package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.other.StatM;
import com.example.demo.repository.MessRepos2;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class DemoServ {

    @Value("${upload.path}")
    private String uploadPath;

    private final MessRepos2 messRepos2;
    private final UserRepo userRepo;

    public DemoServ(MessRepos2 messRepos2, UserRepo userRepo) {
        this.messRepos2 = messRepos2;
        this.userRepo = userRepo;
    }

    public String popol(String mon, User id, Model model) {

        if (!mon.equals("0") && StatM.itsNumber(mon) && StatM.prov(mon)) {

            id.setMoney(id.getMoney().add(new BigDecimal(mon)));
            userRepo.save(id);

            String res = "Операция выполнена успешно. Поздравляем!";
            model.addAttribute("res", res);
        }
        else {
            String res = "Введите значение не равное 0, в формате 1111.11!";
            model.addAttribute("res", res);
        }

        return "redirect:/all";
    }

    public String init() {

        User user = new User("Dima", "g", true, new BigDecimal("1111.11"),
                Collections.singleton(Role.ADMIN));
        userRepo.save(user);

        return "redirect:/login";
    }

    public String all(Map<String, Object> map, String tag, User user, String res) {

        Iterable<Message> messages;
        if (tag != null && !tag.isEmpty()) {
            messages = messRepos2.findByTag(tag);
            map.put("tag", tag);
        }
        else {
            messages = messRepos2.findAllByIdIsNotNullOrderByIdAsc();
        }
        User user1 = userRepo.findById(user.getId()).orElse(null);

        map.put("all", messages);
        map.put("usr", user1);
        map.put("res", res);

        return "all";
    }

    public String add(Message messageN, User user, MultipartFile file) throws IOException {

        User user1 = userRepo.findById(user.getId()).orElse(null);

        assert user1 != null;
        Message message = new Message(messageN.getText(), messageN.getTag(), user1.getUsername(), user1);

        if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            message.setFilename(resultFileName);
        }

        user1.getMessage().add(message);

        messRepos2.save(message);
        userRepo.save(user1);

        return "redirect:/all";
    }
}
