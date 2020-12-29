/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {

    private NoteService noteService;
    private UserService userService;

    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("notes", noteService.getAllNotes());
        return "home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model) {
//        InputStream fis = fileUpload.getInputStream();
        return "home";
    }

    @PostMapping("/addNote")
    public String addNote(Authentication authentication, @ModelAttribute("note") Note note, Model model) {

        // TODO: This is not really good because there might be two users with the same name
        String loggedInUserName = authentication.getName();
        User loggedIinUser = userService.getUser(loggedInUserName);
        Note newNote = new Note(null, note.getNoteTitle(), note.getNoteDescription(), loggedIinUser.getUserId());

        noteService.addNoteToList(newNote);

        model.addAttribute("notes", noteService.getAllNotes());

        return "home";

    }

    @PostMapping("/logout")
    public String logout(Model model) {
        // TODO: find out why it works
        return "login";
    }
}
