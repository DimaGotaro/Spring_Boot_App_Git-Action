package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.MessRepos2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class MessServ {

    private MessRepos2 messRepos2;

    public MessServ(MessRepos2 messRepos2) {
        this.messRepos2 = messRepos2;
    }

    public String update(Long id, Map<String, Object> map) {

        messRepos2.findById(id).ifPresent(byId -> map.put("obj", byId));

        return "update";
    }

    public String updateP(Message message1, Message message) {

        message1.setText(message.getText());
        message1.setTag(message.getTag());
        messRepos2.save(message1);

        return "redirect:/all";
    }

    public String delete(Message message) {

        if (message.getAuthor() != null) {
            User user = message.getAuthor();

            user.getMessage().remove(message);
            message.setAuthor(null);
        }
        messRepos2.delete(message);

        return "redirect:/all";
    }
}
