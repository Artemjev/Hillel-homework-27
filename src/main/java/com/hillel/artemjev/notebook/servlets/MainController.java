package com.hillel.artemjev.notebook.servlets;

import com.hillel.artemjev.notebook.entities.Note;
import com.hillel.artemjev.notebook.infrastructure.annotations.Autowired;
import com.hillel.artemjev.notebook.infrastructure.annotations.Controller;
import com.hillel.artemjev.notebook.infrastructure.annotations.GetMapping;
import com.hillel.artemjev.notebook.infrastructure.annotations.PostMapping;
import com.hillel.artemjev.notebook.repository.UberFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UberFactory uberFactory;
//    private NotesRepository notesRepository = uberFactory.getNotesRepository(); - так сервлет не создается
//    (наверное потомучто порядок создания филдов не определен)

    @GetMapping("")
    public void notesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Note> notes = uberFactory.getNotesRepository().getAll();
        req.setAttribute("notes", notes);
        req.getRequestDispatcher("WEB-INF/views/notesList.jsp").forward(req, resp);
    }

    @PostMapping("")
    public void addNote(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!req.getParameter("name").isEmpty()) {
            uberFactory.getNotesRepository().add(
                    new Note()
                            .setName(req.getParameter("name"))
                            .setDescription(req.getParameter("description"))
            );
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @GetMapping("notes/{id}")
    public void notePage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String strId = (String) req.getAttribute("id");
        Note note = uberFactory.getNotesRepository().getNote(Integer.valueOf(strId));
        req.setAttribute("note", note);
        req.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/note.jsp")
                .forward(req, resp);
    }

    @PostMapping("delete")
    public void deleteNote(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String strId = req.getParameter("id");
        uberFactory.getNotesRepository().remove(Integer.valueOf(strId));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
