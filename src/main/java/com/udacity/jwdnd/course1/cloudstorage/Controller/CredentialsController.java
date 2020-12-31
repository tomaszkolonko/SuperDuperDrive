/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.Services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialsController {

    private UserService userService;
    private CredentialsService credentialsService;
    private EncryptionService encryptionService;

    public CredentialsController(UserService userService, CredentialsService credentialsService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/addCredentials")
    public String addCredentials(@ModelAttribute Credentials credentials, Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        // TODO: test if it's really null if it does not yet exist


        if (credentials.getCredentialId() != null) {
            // TODO: instead of putting every piece of info into the modals, just qurry them here
            credentialsService.updateCredentials(credentials);
            model.addAttribute("success", true);
            model.addAttribute("message", "Credentials successfully updated.");
            return "result";
        } else {
            credentials.setUserId(user.getUserId());
            // TODO set the encypted password
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(),  encodedKey);

            credentials.setKey(encodedKey);
            credentials.setPassword(encryptedPassword);

            credentialsService.addCredentials(credentials);

            model.addAttribute("success", true);
            model.addAttribute("message", "Credentials successfully added.");

            return "result";
        }
    }

    @GetMapping("/deleteCredentials")
    public String deleteCredentials(@RequestParam Integer id, Authentication authentication, Model model) {
        credentialsService.deleteCredentials(id);

        model.addAttribute("success", true);
        model.addAttribute("message", "Credentials successfully deleted");

        return "result";
    }
}
