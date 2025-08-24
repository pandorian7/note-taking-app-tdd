package com.pandorian.tdd_bdd.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    @Getter(AccessLevel.NONE)
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    public boolean isPasswordBlank() {
        return password.isBlank();
    }

    @OneToMany(mappedBy = "owner")
    @Setter(AccessLevel.NONE)
    private List<Note> notes;
}
