package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.service.gestioneAmministratori.ManageUsersService;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;


@Controller
public class UserDetailsController {

    private static final String ALPHA_NUMERIC_STRING  =
            "ABCDEFGHIJKLMNOPQRSTUVWXY" +
                    "Zabcdefghijklmnopqrstuvwxyz0123456789";
    private final ManageUsersService manageUsersService;
    private final AccountService accountService;

    public UserDetailsController(ManageUsersService manageUsersService, AccountService accountService) {
        this.manageUsersService = manageUsersService;
        this.accountService = accountService;
    }

    @GetMapping("admin/dettagliUtente")
    public String dettUtente(Model model, @RequestParam String username) {
        UtenteDto utente = manageUsersService.findByUsernameOrEmail(username);
        NotificaDto n =new NotificaDto();
        model.addAttribute("utente",utente );
        model.addAttribute("notifica",n);
        return "admin/dettagliUtente";
    }
    public static String generateAlphaNumericString(int length) {

        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(ALPHA_NUMERIC_STRING.
                    charAt(random.nextInt(ALPHA_NUMERIC_STRING.length())));
        }

        return builder.toString();
    }
    @PostMapping("/admin/modifyUser")
    public String modifyUser(@Valid @RequestParam String username) {
           String nusername;
           String alphanum;


       do {

            alphanum= generateAlphaNumericString(15);
            nusername="username"+alphanum;
        }while (accountService.existsByUsername(nusername));



        Utente u = accountService.findByUsernameOrEmail(username);
        u.setUsername(nusername);
        manageUsersService.updateUtenteUsername(u);



            return "redirect:/admin/dettagliUtente?username="+u.getUsername();
    }

}
