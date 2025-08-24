package com.pandorian.tdd_bdd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "notes")
@NoArgsConstructor
@RequiredArgsConstructor
public class Note {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @ManyToOne
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public void setOwner(User user) {
        if (this.owner != null) {
            throw new IllegalStateException("Note already has a owner");
        }
        this.owner = user;
    }
}
