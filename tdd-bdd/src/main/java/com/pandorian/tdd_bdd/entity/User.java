package com.pandorian.tdd_bdd.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "notes")
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

    @OneToMany(mappedBy = "owner")
    @Setter(AccessLevel.NONE)
    private List<Note> notes;

    public boolean isPasswordBlank() {
        return password.isBlank();
    }

    public int passwordLength() {
        return password.length();
    }

    public boolean isPasswordNull() {
        return password == null;
    }

    public boolean validatePassword() {
        if (password == null) {
            return true;
        }
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSymbol = password.matches(".*[!@#$%^&*()_+\\-={};':\"|,.<>/?\\[\\]\\\\].*");

        return !hasLower || !hasUpper || !hasNumber || !hasSymbol;
    }
}
