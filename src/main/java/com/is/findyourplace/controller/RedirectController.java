package com.is.findyourplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {
    @RequestMapping(value = {"", "/index"})
    public String home(Model model) {
        return "index";
    }

    @RequestMapping(value = {"/serverError"})
    public String serverError(Model model) {
        model.addAttribute("errorMessage", "serverError");
        return "error";
    }
}
