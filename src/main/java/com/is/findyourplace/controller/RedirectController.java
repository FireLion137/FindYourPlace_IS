package com.is.findyourplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Gestisce eventuali redirect generali.
 */
@Controller
public class RedirectController {
    /**
     * Mapping della homepage.
     * @return index.html
     */
    @RequestMapping(value = {"", "/index"})
    public String home() {
        return "index";
    }

    /**
     * Mapping della pagina di serverError.
     * @param model Model
     * @return error.html
     */
    @RequestMapping(value = {"/serverError"})
    public String serverError(final Model model) {
        model.addAttribute("errorMessage", "serverError");
        return "error";
    }
}
