package com.example.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@Table(name="POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDate creationDate;
    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Post() {

    }
}
