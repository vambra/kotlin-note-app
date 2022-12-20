package lt.viko.eif.mobiliosiosprogrameles.noteapi.controller;

import lt.viko.eif.mobiliosiosprogrameles.noteapi.dao.NotesDao;
import lt.viko.eif.mobiliosiosprogrameles.noteapi.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    private NotesDao notesDao;

    @GetMapping(path = "", produces = "application/json")
    public Notes getNotes()
    {
        return notesDao.getNotes();
    }

    @PostMapping(path = "", consumes = "application/json")
    public void setNotes(@RequestBody Notes notes)
    {
        notesDao.setNotes(notes);
    }
}