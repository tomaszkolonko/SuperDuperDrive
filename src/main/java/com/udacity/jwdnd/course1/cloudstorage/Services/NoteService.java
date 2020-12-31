/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNoteToList(Note note) {
        noteMapper.insertNote(note);
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public Note getNoteById(Integer id) { return noteMapper.getNoteById(id); }

    public void updateNote(Note note) { noteMapper.updateNote(note); }

    public void deleteNote(Integer id) { noteMapper.deleteNote(id); }
}
