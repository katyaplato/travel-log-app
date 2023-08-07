package com.example.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "USERS")
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String profilePicture;
    private String bio;
    private LocalDate creationDate;
    private UUID refreshTokenUUID;

    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "verification_tokens_id")
    private VerificationToken verificationToken;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL)
    private final List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Post> posts = new ArrayList<>();


    public User(String username, String email, String password, String fullName, String profilePicture, String bio) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.creationDate = LocalDate.now();

    }

    public User(User user) {

    }

    public User() {

    }
}
