package com.example.demo.repository;

import com.example.demo.entity.Message;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessRepos2 extends JpaRepository<Message, Long> {

    List<Message> findByTag(String tag);

    List<Message> findByTagOrText(String tag, String text);

    @Query(value = "select m from Message m where m.id = (select max (m.id) from Message m)")
    Message findByMaxId_1();

    @Query(value = "select * from Message where id = (select min (id) from Message)",
    nativeQuery = true)
    Message findByMinId_2();

    @Query(value = "select m from Message m where m.id = :num")
    Message getMess(@Param("num") Long id);

    Message findFirstByIdIsNotNullOrderByIdAsc();

    Message findFirstByIdIsNotNullOrderByIdDesc();

    List<Message> findByTagStartsWith(String f);

    List<Message> findByTextEndingWith(String f);

    List<Message> findByFilenameContaining(String f);

    List<Message> findAllByIdIsNotNullOrderByIdAsc();
}
