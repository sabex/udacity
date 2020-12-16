package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;

    private CredentialService credentialService;
    private NoteService noteService;
    private FileService fileService;

    public HomeController(UserService userService, CredentialService credentialService, NoteService noteService, FileService fileService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {
        model.addAttribute("credentialList", credentialService.getCredentials(userService.getUser(authentication.getName())));
        model.addAttribute("noteList", noteService.getNotes(userService.getUser(authentication.getName())));
        model.addAttribute("fileList", fileService.getFiles(userService.getUser(authentication.getName())));
        return "home";
    }

    @PostMapping("/credential")
    public String addCredential(Authentication authentication, Model model, Credential credential) {
        User user = userService.getUser(authentication.getName());
        Integer result = credentialService.createCredential(credential, user);
        if (result < 1) {
            model.addAttribute("message", "add credential failed");
        }
        return ("redirect:/home");
    }

    @PostMapping("/note")
    public String addNote(Authentication authentication, Model model, Note note) {
        log.warn(note.toString());
        return "home";
    }

}
