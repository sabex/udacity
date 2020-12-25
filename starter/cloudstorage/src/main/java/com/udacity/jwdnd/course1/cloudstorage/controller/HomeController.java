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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
  public View addOrUpdateCredential(
      Authentication authentication, Model model, Credential credential, RedirectAttributes redirectAttributes) {
    User user = userService.getUser(authentication.getName());
    // check if update or create by checking value of credential id
    if (null != credential.getCredentialid() && credential.getCredentialid() > 0) { // update
      Integer result = credentialService.updateCredential(credential, user);
      displayMessage = (result < 1) ? "update credential failed" : "Credential updated";
    } else { // create
      Integer result = credentialService.createCredential(credential, user);
      displayMessage = (result < 1) ? "Add credential failed" : "Credential added";
    }
    redirectAttributes.addFlashAttribute("message",displayMessage);
    return new RedirectView("/home");
  }

  @GetMapping("/credential/{credentialId}")
  public View deleteCredential(
      @PathVariable Integer credentialId, Authentication authentication, RedirectAttributes redirectAttributes) {
    User user = userService.getUser(authentication.getName());
    Integer result = credentialService.deleteCredential(credentialId, user);
    redirectAttributes.addFlashAttribute("message","Credential deleted successfully");
    return new RedirectView("/home");
  }

  @PostMapping("/note")
  public View addOrUpdateNote(Authentication authentication, Model model, Note note, RedirectAttributes redirectAttributes) {
    User user = userService.getUser(authentication.getName());
    if (null != note.getNoteid() && note.getNoteid() > 0) { // update
      Integer result = noteService.updateNote(note, user);
      displayMessage = (result < 1) ? "update note failed" : "Note updated";
    } else {
      Integer result = noteService.createNote(note, user);
      displayMessage = (result < 1) ? "Add note failed" : "Note added";
    }
    redirectAttributes.addFlashAttribute("message",displayMessage);
    return new RedirectView("/home");
  }

  @GetMapping("/note/{noteId}")
  public View deleteNote(@PathVariable Integer noteId, Authentication authentication, RedirectAttributes redirectAttributes) {
    User user = userService.getUser(authentication.getName());
    Integer result = noteService.deleteNote(noteId, user);
    redirectAttributes.addFlashAttribute("message","Note deleted successfully");
    return new RedirectView("/home");
  }

  @PostMapping("/file")
  public View addFile(
          Authentication authentication,
          @RequestParam("fileUpload") MultipartFile uploadedFile, RedirectAttributes redirectAttributes)
      throws IOException {
    User user = userService.getUser(authentication.getName());
    try{
      Integer result = fileService.createFile(uploadedFile, user);
      displayMessage = (result < 1) ? "Add file failed" : "File added";
    }
    catch (Exception ex) {
      displayMessage = ex.getMessage();
    }
    redirectAttributes.addFlashAttribute("message",displayMessage);
    return new RedirectView("/home");
  }

  @GetMapping("/file/{fileId}")
  public View deleteFile(@PathVariable Integer fileId, Authentication authentication, RedirectAttributes redirectAttributes) {
    User user = userService.getUser(authentication.getName());
    Integer result = fileService.deleteFile(fileId, user);
    redirectAttributes.addFlashAttribute("message" ,"File deleted successfully");
    return new RedirectView("/home");
  }

  @GetMapping("/file/download/{fileId}")
  public ResponseEntity downloadFile(@PathVariable Integer fileId, Authentication authentication) {
    User user = userService.getUser(authentication.getName());
    File file = fileService.getFile(user, fileId);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(file.getContentType()))
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
        .body(new ByteArrayResource(file.getFileData()));
  }
}
