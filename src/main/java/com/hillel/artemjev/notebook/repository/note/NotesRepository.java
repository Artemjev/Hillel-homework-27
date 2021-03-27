package com.hillel.artemjev.notebook.repository.note;

import com.hillel.artemjev.notebook.entities.Note;

import java.util.List;

public interface NotesRepository {
    Note getNote(Integer id);

    List<Note> getAll();

    void add(Note note);

    void remove(Integer id);
}
