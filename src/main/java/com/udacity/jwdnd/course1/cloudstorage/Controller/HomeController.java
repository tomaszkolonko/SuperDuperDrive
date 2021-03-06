/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.Services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.Services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.Services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.Services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private CredentialsService credentialsService;
    private EncryptionService encryptionService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService, CredentialsService credentialsService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getHome(Authentication authentication, Model model) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);

        model.addAttribute("note", new Note());
        model.addAttribute("notes", noteService.getAllNotes());

        model.addAttribute("credentials", new Credentials());
        model.addAttribute("allCredentials", credentialsService.getAllCredentialsByUserId(user.getUserId()));

        model.addAttribute("encryptionService", encryptionService);

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

        User user = userService.getUser(authentication.getName());
        File file = fileService.getFileByFileName(fileUpload.getOriginalFilename(), user.getUserId());
        if(file != null) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Sorry, filename already exists in DB. Try another name.");
            return "result";
        }

        file = new File();
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

    @GetMapping("/downloadFile")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam Integer id, Authentication authentication, Model model) {
        File file = fileService.getFileByFileId(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @PostMapping("/logout")
    public String logout(Model model) {
        // TODO: find out why it works
        return "login";
    }
}
