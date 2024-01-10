package com.is.findyourplace.controller.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import jakarta.validation.Valid;
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
        // create model object to store form data
        UtenteDto u = new UtenteDto();
        model.addAttribute("utente", u);
        return "account/accountAuth";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("utente") UtenteDto utenteDto,
                               BindingResult result,
                               Model model) {
        if(accountService.existsByUsername(utenteDto.getUsername())){
            result.rejectValue("username", "null",
                    "Username già usato!");
        }

        if(accountService.existsByEmail(utenteDto.getEmail())){
            result.rejectValue("username", "null",
                    "Email già usata!");
        }

        if(result.hasErrors()){
            model.addAttribute("utente", utenteDto);
            return "account/accountAuth";
        }

        accountService.saveUtente(utenteDto);
        return "redirect:/";
    }
}
