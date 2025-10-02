package com.pandorian.tdd_bdd.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString(exclude = "notes")
public class User {

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String username;

    @Transient
    @NonNull
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String password;

    public void setPassword(String password) {
        this.password = password;
        this.passwordHash = DigestUtils.sha256Hex(password);
    }

    public boolean checkPassword(String password) {
        return this.passwordHash.equals(DigestUtils.sha256Hex(password));
    }

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String passwordHash;

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
