package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@JsonAutoDetect
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не должно быть пустым!")
    @Size(min = 1, max = 255, message = "Поле должно содержать от 1 до 255 знаков")
    @Column
    private String text;
    @NotEmpty(message = "Поле не должно быть пустым!")
    @Size(min = 1, max = 20, message = "Поле должно содержать от 1 до 20 знаков")
    @Column
    private String tag;
    @Column
    private String authorName;
    @Column
    private String filename;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User author;

    public Message(String text, String tag, String authorName, User author) {
        this.text = text;
        this.tag = tag;
        this.authorName = authorName;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", tag='" + tag + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }

    @JsonIgnore
    public String getAutName() {
        return getAuthorName() != null ? getAuthorName() : "<none>";
    }
}
