package com.hillel.artemjev.notebook.repository;


import com.hillel.artemjev.notebook.infrastructure.annotations.Component;
import com.hillel.artemjev.notebook.repository.note.NotesRepository;
import com.hillel.artemjev.notebook.repository.note.NotesRepositoryDbImpl;
import com.hillel.artemjev.notebook.util.jdbcquery.JdbcTemplate;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

@Component
public class UberFactory {
    private final NotesRepository notesRepository;

    public UberFactory() {
        String dsn = "jdbc:postgresql://localhost:5432/notebook";
        String user = "postgres";
        String password = "0000";
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dsn);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(8);
        config.setMinimumIdle(4);
        config.setDriverClassName("org.postgresql.Driver");
        DataSource dataSource = new HikariDataSource(config);
        notesRepository = new NotesRepositoryDbImpl(
                new JdbcTemplate(dataSource)
        );
    }

    public NotesRepository getNotesRepository() {
        return notesRepository;
    }
}
