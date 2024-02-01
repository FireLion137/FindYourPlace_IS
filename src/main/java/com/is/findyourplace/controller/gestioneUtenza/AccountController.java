package com.is.findyourplace.controller.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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
     * Variabile usata per definire quando questo controller
     * viene usato per un test.
     */
    @Value("${app.testMode:false}")
    private boolean testMode;

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
     * @param request HttpServletRequest
     * @return account/accountAuth.html
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registration(
            @Valid @ModelAttribute("utenteR") final UtenteDto utenteDto,
            final BindingResult result,
            final HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (accountService.existsByUsername(utenteDto.getUsername())) {
            result.rejectValue("username", "null",
                    "Username già usato!");
        }

        if (accountService.existsByEmail(utenteDto.getEmail())) {
            result.rejectValue("email", "null",
                    "Email già usata!");
        }

        if (utenteDto.getPassword() == null
                || utenteDto.getPassword().isBlank()) {
            result.rejectValue("password", "null",
                    "Pattern Password errato!");
        }

        if (result.hasErrors()) {
            response.put("errors", result.getAllErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        accountService.saveUtente(utenteDto);

        // Login automatico dopo la registrazione
        if (!testMode) {
            try {
                request.login(utenteDto.getUsername(), utenteDto.getPassword());
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
