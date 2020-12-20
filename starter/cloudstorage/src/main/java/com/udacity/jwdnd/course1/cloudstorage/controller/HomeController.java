package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

  private UserService userService;

  private CredentialService credentialService;
  private NoteService noteService;
  private FileService fileService;

  private String displayMessage;

  public HomeController(
      UserService userService,
      CredentialService credentialService,
      NoteService noteService,
      FileService fileService) {
    this.userService = userService;
    this.credentialService = credentialService;
    this.fileService = fileService;
    this.noteService = noteService;
  }

  @GetMapping
  public String getHomePage(Authentication authentication, Model model) {
    if (displayMessage != null) {
      model.addAttribute("message", displayMessage);
    }
    model.addAttribute(
        "credentialList",
        credentialService.getCredentials(userService.getUser(authentication.getName())));
    model.addAttribute(
        "noteList", noteService.getNotes(userService.getUser(authentication.getName())));
    model.addAttribute(
        "fileList", fileService.getFiles(userService.getUser(authentication.getName())));
    displayMessage = null; // reset message
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

  @GetMapping("/credential/{credentialId}")
  public String deleteCredential(@PathVariable Integer credentialId, Authentication authentication) {
    User user = userService.getUser(authentication.getName());
    Integer result = credentialService.deleteCredential(credentialId, user);
    return "redirect:/home";
  }

  @PostMapping("/note")
  public String addNote(Authentication authentication, Model model, Note note) {
    User user = userService.getUser(authentication.getName());
    Integer result = noteService.createNote(note, user);
    if (result < 1) {
      model.addAttribute("message", "add note Failed");
    } else {
      model.addAttribute("message", "File " + note.getNoteTitle() + " Added successfully");
    }

    return "redirect:/home";
  }

  @GetMapping("/note/{noteId}")
  public String deleteNote(@PathVariable Integer noteId, Authentication authentication) {
    User user = userService.getUser(authentication.getName());
    Integer result = noteService.deleteNote(noteId, user);
    return "redirect:/home";
  }

  @PostMapping("/file")
  public String addFile(
      Authentication authentication,
      @RequestParam("fileUpload") MultipartFile uploadedFile,
      Model model) {
    User user = userService.getUser(authentication.getName());
    try {
      Integer result = fileService.createFile(uploadedFile, user);
      if (result < 1) {
        displayMessage = "add file Failed";
      }
    } catch (Exception exception) {
      displayMessage = exception.getMessage();
    }
    return "redirect:/home";
  }

  @GetMapping("/file/{fileId}")
  public String deleteFile(@PathVariable Integer fileId, Authentication authentication) {
    User user = userService.getUser(authentication.getName());
    Integer result = fileService.deleteFile(fileId, user);
    return "redirect:/home";
  }
}
