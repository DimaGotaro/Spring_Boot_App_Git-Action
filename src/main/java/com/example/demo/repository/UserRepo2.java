package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo2 extends CrudRepository<User, Long> {
}
