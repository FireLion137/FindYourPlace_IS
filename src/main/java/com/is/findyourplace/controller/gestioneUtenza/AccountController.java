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

//Gestisce Registrazione, Login e Logout di un Utente
@Controller
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // handler method to handle user registration and login form request
    @GetMapping("/accountAuth")
    public String AccountAuthForm(Model model){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth != null &&
                auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken))
            return "redirect:/";
        // create model object to store form data
        UtenteDto uR = new UtenteDto();
        UtenteDto uL = new UtenteDto();
        model.addAttribute("utenteR", uR);
        model.addAttribute("utenteL", uL);
        return "account/accountAuth";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("utenteR") UtenteDto utenteDto,
                               BindingResult result,
                               Model model,
                               HttpServletRequest request) {
        if(accountService.existsByUsername(utenteDto.getUsername())){
            result.rejectValue("username", "null",
                    "Username già usato!");
        }

        if(accountService.existsByEmail(utenteDto.getEmail())){
            result.rejectValue("email", "null",
                    "Email già usata!");
        }

        if(result.hasErrors()){
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
