package com.example.demo.rest_contrl;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.MessRepos;
import com.example.demo.repository.MessRepos2;
import com.example.demo.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rest")
public class Demo_RestContr {

    private final MessRepos messRepos;
    private final MessRepos2 messRepos2;
    private final UserRepo userRepo;

    public Demo_RestContr(MessRepos messRepos, MessRepos2 messRepos2, UserRepo userRepo) {
        this.messRepos = messRepos;
        this.messRepos2 = messRepos2;
        this.userRepo = userRepo;
    }
    @GetMapping("/m")
    public List<Message> mess() {
        return messRepos2.findAll();
    }
    @GetMapping("/u")
    public List<User> usr() {
        return userRepo.findAll();
    }
    @GetMapping("/u/{id}")
    public User usr_id(@PathVariable(name = "id") Long id) {
        return userRepo.findById(id).orElse(null);
    }
    @GetMapping("/m1")
    public String mess1() {
        List<Message> list = messRepos2.findAll();

        StringWriter stringWriter = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(stringWriter, list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String s = stringWriter.toString();
        return s;
    }
    @GetMapping("/m/{id}")
    public EntityModel<Message> one(@PathVariable Long id) {

        Message employee = messRepos2.findById(id).orElse(null);

        assert employee != null;
        EntityModel<Message> mall2 = EntityModel.of(employee,
                linkTo(methodOn(Demo_RestContr.class).one(id)).withSelfRel(),
                linkTo(methodOn(Demo_RestContr.class).all()).withRel("mall"));
        return mall2;
    }
    @GetMapping("/mall")
    CollectionModel<EntityModel<Message>> all() {

        List<EntityModel<Message>> employees = messRepos2.findAll().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(Demo_RestContr.class).one(employee.getId())).withSelfRel(),
                        linkTo(methodOn(Demo_RestContr.class).all()).withRel("mall")))
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(Demo_RestContr.class).all()).withSelfRel());
    }
    @GetMapping("/ex")
    public List<Message> ex() {
        return messRepos2.findByTagOrText("срочно", "Забулдыга");
    }
    @GetMapping("/ex1")
    public Message ex1() {
        return messRepos2.findByMaxId_1();
    }
    @GetMapping("/ex2")
    public Message ex2() {
        return messRepos2.findByMinId_2();
    }
    @GetMapping("/ex3")
    public Message ex3() {
        return messRepos2.findFirstByIdIsNotNullOrderByIdAsc();
    }
    @GetMapping("/ex4")
    public Message ex4() {
        return messRepos2.findFirstByIdIsNotNullOrderByIdDesc();
    }
    @GetMapping("/ex5")
    public List<Message> ex5() {
        return messRepos2.findByTagStartsWith("н");
    }
    @GetMapping("/ex6")
    public List<Message> ex6() {
        return messRepos2.findByTextEndingWith("а");
    }
    @GetMapping("/ex7")
    public List<Message> ex7() {
        return messRepos2.findByFilenameContaining("Снимок экрана");
    }
    @GetMapping("/ex8")
    public Message ex8() {
        return messRepos2.getMess(2L);
    }
}
