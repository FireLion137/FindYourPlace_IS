package com.is.findyourplace.controller.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Gestisce Registrazione, Login e Logout di un Utente.
 */
@Controller
public class AccountController {
    /**
     * Service usato per l'autenticazione.
     */
    private final AccountService accountService;

    /**
     * Costruttore del controller.
     * @param accountService AccountService
     */
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Mapping method to handle user registration and login form request.
     * @param model Model
     * @return account/accountAuth.html
     */
    @GetMapping("/accountAuth")
    public String accountAuthForm(final Model model) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        // create model object to store form data
        UtenteDto uR = new UtenteDto();
        UtenteDto uL = new UtenteDto();
        model.addAttribute("utenteR", uR);
        model.addAttribute("utenteL", uL);
        return "account/accountAuth";
    }

    /**
     * Mapping method to handle user registration form submit request.
     * @param utenteDto UtenteDto con tutti i dati.
     * @param result BindingResult, contiene gli errori.
     * @param model Model
     * @param request HttpServletRequest
     * @return account/accountAuth.html
     */
    @PostMapping("/register")
    public String registration(
            @Valid @ModelAttribute("utenteR") final UtenteDto utenteDto,
            final BindingResult result,
            final Model model,
            final HttpServletRequest request) {
        if (accountService.existsByUsername(utenteDto.getUsername())) {
            result.rejectValue("username", "null",
                    "Username già usato!");
        }

        if (accountService.existsByEmail(utenteDto.getEmail())) {
            result.rejectValue("email", "null",
                    "Email già usata!");
        }

        if (utenteDto.getPassword().isBlank()) {
            result.rejectValue("password", "null",
                    "Pattern Password errato!");
        }

        if (result.hasErrors()) {
            model.addAttribute("utenteR", utenteDto);
            model.addAttribute("utenteL", new UtenteDto());
            return "account/accountAuth";
        }

        accountService.saveUtente(utenteDto);
        try {
            request.login(utenteDto.getUsername(), utenteDto.getPassword());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}
