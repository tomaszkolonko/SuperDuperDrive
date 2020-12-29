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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

//    @PostMapping("/addNote")
//    public String addNote(Authentication authentication, @ModelAttribute("note") Note note, Model model) {
//
//        // TODO: This is not really good because there might be two users with the same name
//        String loggedInUserName = authentication.getName();
//        User loggedIinUser = userService.getUser(loggedInUserName);
//        Note newNote = new Note(null, note.getNoteTitle(), note.getNoteDescription(), loggedIinUser.getUserId());
//
//        noteService.addNoteToList(newNote);
//
//        model.addAttribute("notes", noteService.getAllNotes());
//
//        return "home";
//
//    }

    @PostMapping("/addNote")
    public String createOrUpdateNote(@ModelAttribute("note") Note note, Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        int userId = userService.getUser(username).getUserId();
        note.setUserId(userId);
        if (note.getNoteId() != null) {
            try {
                noteService.updateNote(note);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was updated successful.");
                return "redirect:/result";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note update. Please try again!");
                return "redirect:/result";
            }
        } else {
            try {
                noteService.addNoteToList(note);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was created successful.");
                return "redirect:/result";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note update. Please try again!");
                return "redirect:/result";
            }
        }
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam Integer id, Model model) {
        noteService.deleteNote(id);
        model.addAttribute("note", new Note());
        model.addAttribute("notes", noteService.getAllNotes());
        return "home";

    }

}
