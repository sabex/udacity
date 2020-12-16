package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;

    private CredentialService credentialService;

    public HomeController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {
        // get list of files for user and add to model
        // get list of notes for user and add to model
        // get list of credentials for user add to model
        return "home";
    }

    @PostMapping("/credential")
    public String addCredential(Authentication authentication, Model model, Credential credential) {
        User user = userService.getUser(authentication.getName());
        Integer result = credentialService.createCredential(credential, user);
        if (result < 1) {
            model.addAttribute("message", "add credential failed");
        }
        else {
            model.addAttribute(credentialService.getCredentials(user));
        }
        return "redirect:/home";
    }

    @PostMapping("/note")
    public String addNote(Authentication authentication, Model model, Note note) {
        log.warn(note.toString());
        return "home";
    }

}
