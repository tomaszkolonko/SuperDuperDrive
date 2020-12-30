/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.Services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialsController {

    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialsController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/addCredentials")
    public String addCredentials(@ModelAttribute Credentials credentials, Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        credentials.setUserId(user.getUserId());

        // TODO set the encypted password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(),  encodedKey);

        credentials.setKey(encodedKey);
        credentials.setPassword(encryptedPassword);

        credentialService.addCredentials(credentials);

        model.addAttribute("success", true);
        model.addAttribute("message", "Credentials successfully added.");

        return "result";

    }

}
