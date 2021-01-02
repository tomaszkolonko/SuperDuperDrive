/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2021.
 */

package com.udacity.jwdnd.course1.cloudstorage.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleTooBigFiles(MaxUploadSizeExceededException e, Model model) {
        model.addAttribute("success", false);
        model.addAttribute("message", "Sorry, file must be smaller than 1 MB.");
        return "result";
    }
}
