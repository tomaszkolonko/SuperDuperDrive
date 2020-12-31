/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/addNote")
    public String createOrUpdateNote(@ModelAttribute("note") Note note, Authentication authentication, Model model) {
        String username = authentication.getName();
        int userId = userService.getUser(username).getUserId();
        note.setUserId(userId);
        if (note.getNoteId() != null) {
            try {
                noteService.updateNote(note);
                model.addAttribute("success", true);
                model.addAttribute("message", "Your note was updated successful.");
                return "result";
            } catch (Exception e) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Something went wrong updating your note. Please try again.");
                return "result";
            }
        } else {
            try {
                noteService.addNoteToList(note);
                model.addAttribute("success", true);
                model.addAttribute("message", "Your note has been successfully added.");
                return "result";
            } catch (Exception e) {
                model.addAttribute("success", false);
                model.addAttribute("message", "Something went wrong creating your note. Please try again.");
                return "result";
            }
        }
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam Integer id, Model model) {
        noteService.deleteNote(id);
        model.addAttribute("success", true);
        model.addAttribute("message", "Note was successfully deleted.");
        return "result";

    }

}
