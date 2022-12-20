package com.example.demo.repository;

import com.example.demo.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.TreeSet;

public interface MessRepos extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);
}
