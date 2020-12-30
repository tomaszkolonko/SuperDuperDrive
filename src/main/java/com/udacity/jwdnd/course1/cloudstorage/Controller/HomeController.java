/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.Services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class HomeController {

    private NoteService noteService;
    private UserService userService;
    private FileService fileService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHome(Authentication authentication, Model model) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);

        model.addAttribute("note", new Note());
        model.addAttribute("notes", noteService.getAllNotes());
        model.addAttribute("files", fileService.getFilesByUserId(user.getUserId()));
        return "home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) {
        if (fileUpload.isEmpty()) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Your update didn't work. Please try again.");
            return "result";
        }

        File file = new File();
        try {
            file.setFileId(null);
            file.setFileName(fileUpload.getOriginalFilename());
            file.setContentType(fileUpload.getContentType());
            file.setFileSize(String.valueOf(fileUpload.getSize()));
            User loggedInUser = userService.getUser(authentication.getName());
            file.setUserId(loggedInUser.getUserId());
            file.setFileData(fileUpload.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileService.saveFile(file);
        model.addAttribute("success", true);
        model.addAttribute("message", "Your file has been successfully uploaded.");
        return "result";
    }

    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam Integer id, Model model) {
        fileService.deleteFileByFileId(id);
        model.addAttribute("success", true);
        model.addAttribute("message", "Your file has been successfully deleted.");
        return "result";
    }

    @PostMapping("/logout")
    public String logout(Model model) {
        // TODO: find out why it works
        return "login";
    }
}
