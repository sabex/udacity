package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadControllerAdvice {

        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public View handleMaxSizeException(
                MaxUploadSizeExceededException exc, RedirectAttributes redirectAttributes) {

           redirectAttributes.addFlashAttribute("message", "File too large!");
            return new RedirectView("/home");
        }
    }

