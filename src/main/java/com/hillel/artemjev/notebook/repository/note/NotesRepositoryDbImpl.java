package com.hillel.artemjev.notebook.repository.note;

import com.hillel.artemjev.notebook.entities.Note;
import com.hillel.artemjev.notebook.util.jdbcquery.BeanPropertyRowMapper;
import com.hillel.artemjev.notebook.util.jdbcquery.JdbcTemplate;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class NotesRepositoryDbImpl implements NotesRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Note getNote(Integer id) {
        return jdbcTemplate.queryOne(
                "SELECT id, name, description, date_time FROM notes WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<Note>(Note.class)
        );
    }

    @Override
    public List<Note> getAll() {
        return jdbcTemplate.query(
                "SELECT id, name, description, date_time FROM notes",
                new BeanPropertyRowMapper<Note>(Note.class)
        );
    }

    @Override
    public void add(Note note) {
        jdbcTemplate.update(
                "INSERT INTO notes(name, description, date_time) VALUES(?,?,?)",
                new Object[]{
                        note.getName(),
                        note.getDescription(),
                        note.getDateTime()
                }
        );
    }

    @Override
    public void remove(Integer id) {
        jdbcTemplate.update(
                "DELETE FROM notes WHERE id=?",
                new Object[]{id}
        );
    }
}
