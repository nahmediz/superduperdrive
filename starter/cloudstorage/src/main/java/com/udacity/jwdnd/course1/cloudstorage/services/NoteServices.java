package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class NoteServices {

    private NoteMapper noteMapper;

    public NoteServices(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note getNote(int noteId) {
        return noteMapper.getNote(noteId);
    }

    public int addNote(@NotNull Note note) {
        return noteMapper.addNote(note);
    }

    public boolean deleteNote(@NotNull Note note) {
        if (noteMapper.getNote(note.getNoteid()) != null) {
            noteMapper.deleteNote(note);
            return true;
        } else {
            return false;
        }
    }

    public void updateNote(@NotNull Note note) {
        noteMapper.updateNote(note);
    }

    public List<Note> getUserNotes(@NotNull User user) {
        return noteMapper.getUserNotes(user);
    }
}
