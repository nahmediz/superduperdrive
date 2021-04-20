package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServices {

    private NoteMapper noteMapper;

    public NoteServices(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note) {
        return noteMapper.addNote(note);
    }

    public void deleteNote(Note note) {
        noteMapper.deleteNote(note);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public List<Note> getUserNotes(User user) {
        return noteMapper.getUserNotes(user);
    }
}
