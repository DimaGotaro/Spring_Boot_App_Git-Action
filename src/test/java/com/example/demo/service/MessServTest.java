package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.MessRepos2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@TestPropertySource("/applicationTest.properties")
class MessServTest {

    @Autowired
    private MessServ messServ;

    @MockBean
    private MessRepos2 messRepos2;

    @MockBean
    Map<String, Object> map;

    @Test
    void update() {
        Long id = 2L;

        Message message = new Message();
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        Optional<Message> optional = messages.stream().findAny();

        Mockito.doReturn(optional)
                .when(messRepos2)
                .findById(id);

        String s = messServ.update(id, map);

        Assertions.assertEquals("update", s);
        Mockito.verify(messRepos2, Mockito.times(1)).findById(id);
        Mockito.verify(map, Mockito.times(1)).put(ArgumentMatchers.any(String.class),
                ArgumentMatchers.any(Message.class));
    }

    @Test
    void update2() {
        Long id = 2L;

        String s = messServ.update(id, map);

        Assertions.assertEquals("update", s);
        Mockito.verify(messRepos2, Mockito.times(1)).findById(id);
        Mockito.verify(map, Mockito.times(0)).put(ArgumentMatchers.any(String.class),
                ArgumentMatchers.any(Message.class));
    }

    @Test
    void updateP() {
        Message message = new Message();
        message.setText("abc");
        message.setTag("cba");
        Message message1 = new Message();

        String s = messServ.updateP(message1, message);

        Assertions.assertEquals("redirect:/all", s);
        Assertions.assertNotNull(message1.getText());
        Assertions.assertNotNull(message1.getTag());
        Mockito.verify(messRepos2, Mockito.times(1)).save(message1);
    }

    @Test
    void delete() {
        Message message = new Message();

        String s = messServ.delete(message);

        Assertions.assertEquals("redirect:/all", s);
        Assertions.assertNull(message.getAuthor());
        Mockito.verify(messRepos2, Mockito.times(1)).delete(message);
    }

    @Test
    void delete2() {
        Message message = new Message();
        message.setAuthor(new User());

        String s = messServ.delete(message);

        Assertions.assertEquals("redirect:/all", s);
        Assertions.assertNull(message.getAuthor());
        Mockito.verify(messRepos2, Mockito.times(1)).delete(message);
    }
}