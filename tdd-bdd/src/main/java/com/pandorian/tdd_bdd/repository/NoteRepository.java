package com.pandorian.tdd_bdd.repository;

import com.pandorian.tdd_bdd.entity.Note;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    boolean existsById(@NonNull Long id);
}
