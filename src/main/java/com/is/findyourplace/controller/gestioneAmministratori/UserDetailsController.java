package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.service.gestioneAmministratori.EmailService;
import com.is.findyourplace.service.gestioneAmministratori.ManageUsersService;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;


@Controller
public class UserDetailsController {

    @Autowired
    private EmailService emailService;

    private static final String PassNumb  ="0123456789";
    private static final String PassUppercase  ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String PassSpecialChar  ="!@#&()[{}]:;',?*~$^-+=<>";
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
    public static String generatePassSpecialChar(int length) {

        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);


        for (int i = 0; i < length; i++) {
            builder.append(PassSpecialChar.
                    charAt(random.nextInt(PassSpecialChar.length())));
        }

        return builder.toString();
    }
    public static String generatePassUppercase(int length) {

        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);


        for (int i = 0; i < length; i++) {
            builder.append(PassUppercase.
                    charAt(random.nextInt(PassUppercase.length())));
        }

        return builder.toString();
    }
    public static String generatePassNumb(int length) {

        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);


        for (int i = 0; i < length; i++) {
            builder.append(PassNumb.
                    charAt(random.nextInt(PassNumb.length())));
        }

        return builder.toString();
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
           String email,alphanum,message, subject="Reset dell'username | FindYourPlace";



       do {

            alphanum= generateAlphaNumericString(15);
            nusername="username"+alphanum;
        }while (accountService.existsByUsername(nusername));



        Utente u = accountService.findByUsernameOrEmail(username);
        u.setUsername(nusername);
        manageUsersService.updateUtenteUsername(u);
        message="Salve "+u.getNome()+" "+u.getCognome()+" il tuo username è stato cambiato in \n"+nusername;
        email=u.getEmail();
        emailService.sendEmail(email,subject,message);

            return "redirect:/admin/dettagliUtente?username="+u.getUsername();
    }
    @PostMapping("/admin/modifyPassword")
        public String modifyPassword(@Valid @RequestParam String email){
        Utente u = accountService.findByUsernameOrEmail(email);
        String message, subject="Richiesta di password provvisoria | FindYourPlace",newpassword;

        newpassword=generatePassUppercase(1)+generateAlphaNumericString(8)+generatePassNumb(1)+generatePassSpecialChar(1);

        message="Salve "+u.getNome()+" "+u.getCognome()+" ecco la tua nuova password \n"+newpassword;
        manageUsersService.updateUtentePassword(u,newpassword);
        emailService.sendEmail(email,subject,message);
        return "redirect:/admin/dettagliUtente?username="+u.getUsername();
        }



}
