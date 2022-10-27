package com.noteApp.noteApp.controller;

import com.noteApp.noteApp.exception.ResourceNotFoundException;
import com.noteApp.noteApp.model.Notes;
import com.noteApp.noteApp.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class NotesController {
    @Autowired
    private NotesRepository notesRepository;

    public NotesController() {
        // TODO Auto-generated constructor stub
    }

    @GetMapping("/notes")
    public List<Notes> getNotes() {
        List<Notes> list = notesRepository.findAll();
        list.sort((Notes n1,Notes n2)->{
            if(n2.getPinned().equals(n1.getPinned())) return n2.getTimeCreate().compareTo(n1.getTimeCreate());
            return n2.getPinned().compareTo(n1.getPinned());
        });
        return list;
    }

    @GetMapping("/notesU/{id}")
    public List<Notes> getAllNotesOfUser(@PathVariable(value = "id") Integer Id) {
        List<Notes> noteOfUser = new ArrayList<>();
        List<Notes> list = notesRepository.findAll();
        noteOfUser.addAll(list.stream()
                .filter(n -> n.getUserId().equals(String.valueOf(Id)))
                .collect(Collectors.toList()));
        noteOfUser.sort((Notes n1,Notes n2)->{
            if(n2.getPinned().equals(n1.getPinned())) return n2.getTimeCreate().compareTo(n1.getTimeCreate());
            return n2.getPinned().compareTo(n1.getPinned());
        });
        return noteOfUser;
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<Notes> getNote(@PathVariable(value = "id") Integer Id) throws ResourceNotFoundException {
        Notes note = notesRepository.findById(Id).orElseThrow(()->new ResourceNotFoundException("not found"));
        return ResponseEntity.ok().body(note);
    }

    @PostMapping("/notes")
    public Notes addNote(@RequestBody Notes note) {
        System.out.println("Server Side Created");
        return this.notesRepository.save(note);
    }

    @PutMapping("/notes")
    public ResponseEntity<Notes> updateNote(@RequestBody Notes note) throws ResourceNotFoundException {
        System.out.println("Server Side Editting" + note.getId());
        Notes noteCurrent = notesRepository.findById(note.getId()).orElseThrow(()->new ResourceNotFoundException(("not found")));
        noteCurrent.setId(note.getId());
        noteCurrent.setTitle(note.getTitle());
        noteCurrent.setNotes(note.getNotes());
        noteCurrent.setImg(note.getImg());
        noteCurrent.setPinned(note.getPinned());
        noteCurrent.setTimeCreate(note.getTimeCreate());
        noteCurrent.setUserId(note.getUserId());
        return ResponseEntity.ok(this.notesRepository.save(noteCurrent));
    }

    @PutMapping("/notes/{Id}")
    public ResponseEntity<Notes> updatePinned(@PathVariable(value = "Id") Integer Id) throws ResourceNotFoundException {
        System.out.println("Server Side Updated Pinned" + Id);
        Notes noteCurrent = notesRepository.findById(Id).orElseThrow(()->new ResourceNotFoundException(("not found")));
        noteCurrent.setPinned(!noteCurrent.getPinned());
        return ResponseEntity.ok(this.notesRepository.save(noteCurrent));
    }

    @DeleteMapping("/notes/{Id}")
    public Map<String,Boolean> deleteUser(@PathVariable("Id") Integer Id) throws ResourceNotFoundException {
        System.out.println("Server Side Deleted" + Id);
        Notes noteCurrent = notesRepository.findById(Id).orElseThrow(()->new ResourceNotFoundException(("not found")));
        this.notesRepository.delete(noteCurrent);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }


}
