package com.example.backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

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
//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "verification_tokens_id")
    private VerificationToken verificationToken;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL)
    private final List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Post> posts = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String email, String password, String fullName, String profilePicture, String bio, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.creationDate = LocalDate.now();
        this.role = role;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
