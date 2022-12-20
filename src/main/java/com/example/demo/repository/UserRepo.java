package com.example.demo.repository;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByIdIsNotNullOrderByIdAsc();
}
