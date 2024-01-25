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

/**
 * Gestisce la visualizzazione e modifica parametri
 * di un utente specifico da parte di un Amministratore.
 */
@Controller
public class UserDetailsController {
    /**
     * Service usato per mandare una email.
     */
    @Autowired
    private EmailService emailService;

    /**
     * Numeri consentiti in una password.
     */
    private static final String PASS_NUMB = "0123456789";
    /**
     * Lettere maiuscole consentite in una password.
     */
    private static final String PASS_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Lettere minuscole consentite in una password.
     */
    private static final String PASS_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    /**
     * Caratteri speciali consentiti in una password.
     */
    private static final String PASS_SPECIAL_CHAR = "!@#&()[{}]:;',?*~$^-+=<>";
    /**
     * Stringa alfanumerica.
     */
    private static final String ALPHA_NUMERIC_STRING =
            PASS_UPPERCASE + PASS_LOWERCASE + PASS_NUMB;

    /**
     * Service usato per gestire gli utenti.
     */
    private final ManageUsersService manageUsersService;

    /**
     * Service usato per gli account degli utenti.
     */
    private final AccountService accountService;

    /**
     * Costruttore del controller.
     * @param manageUsersService ManageUsersService
     * @param accountService AccountService
     */
    public UserDetailsController(
            final ManageUsersService manageUsersService,
            final AccountService accountService) {
        this.manageUsersService = manageUsersService;
        this.accountService = accountService;
    }

    /**
     * Mapping pagina per visualizzare i dettagli di un utente.
     * @param model Model
     * @param username String
     * @return admin/dettagliUtente.html
     */
    @GetMapping("admin/dettagliUtente")
    public String dettUtente(
            final Model model,
            @RequestParam final String username) {
        UtenteDto utente = manageUsersService.findByUsernameOrEmail(username);
        NotificaDto n = new NotificaDto();
        model.addAttribute("utente", utente);
        model.addAttribute("notifica", n);
        return "admin/dettagliUtente";
    }

    /**
     * Genera una stringa casuale di caratteri speciali.
     * @param length Lunghezza della stringa
     * @return String
     */
    public static String generatePassSpecialChar(final int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(PASS_SPECIAL_CHAR.
                    charAt(random.nextInt(PASS_SPECIAL_CHAR.length())));
        }
        return builder.toString();
    }

    /**
     * Genera una stringa casuale di caratteri maiuscoli.
     * @param length Lunghezza della stringa
     * @return String
     */
    public static String generatePassUppercase(final int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(PASS_UPPERCASE.
                    charAt(random.nextInt(PASS_UPPERCASE.length())));
        }

        return builder.toString();
    }

    /**
     * Genera una stringa casuale di caratteri minuscoli.
     * @param length Lunghezza della stringa
     * @return String
     */
    public static String generatePassLowercase(final int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(PASS_UPPERCASE.
                    charAt(random.nextInt(PASS_UPPERCASE.length())));
        }

        return builder.toString();
    }

    /**
     * Genera una stringa casuale di numeri.
     * @param length Lunghezza della stringa
     * @return String
     */
    public static String generatePassNumb(final int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(PASS_NUMB.
                    charAt(random.nextInt(PASS_NUMB.length())));
        }

        return builder.toString();
    }

    /**
     * Genera una stringa casuale.
     * @param length Lunghezza della stringa
     * @return String
     */
    public static String generateAlphaNumericString(final int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(ALPHA_NUMERIC_STRING.
                    charAt(random.nextInt(ALPHA_NUMERIC_STRING.length())));
        }

        return builder.toString();
    }

    /**
     * Mapping pagina per visualizzare gli utenti.
     * @param username String
     * @return redirect:/admin/dettagliUtente?username
     */
    @PostMapping("/admin/modifyUser")
    public String modifyUser(
            @Valid @RequestParam final String username) {
        String nusername;
        String alphanum;
        String subject = "Reset Username | FindYourPlace";

        do {
            alphanum = generateAlphaNumericString(15);
            nusername = "username" + alphanum;
        } while (accountService.existsByUsername(nusername));

        Utente u = accountService.findByUsernameOrEmail(username);
        u.setUsername(nusername);
        manageUsersService.updateUtenteUsername(u);
        String message = "Salve " + u.getNome() + " " + u.getCognome()
                + ", il tuo nuovo Username è: \n" + nusername;

        String email = u.getEmail();
        emailService.sendEmail(email, subject, message);
        return "redirect:/admin/dettagliUtente?username=" + u.getUsername();
    }

    /**
     * Mapping pagina per visualizzare gli utenti.
     * @param email String
     * @return redirect:/admin/dettagliUtente?username
     */
    @PostMapping("/admin/modifyPassword")
        public String modifyPassword(
                @Valid @RequestParam final String email) {
        Utente u = accountService.findByUsernameOrEmail(email);

        String subject = "Password provvisoria | FindYourPlace";
        String newpassword = generatePassUppercase(1)
                + generatePassLowercase(1)
                + generateAlphaNumericString(8)
                + generatePassNumb(1)
                + generatePassSpecialChar(1);

        String message = "Salve " + u.getNome() + " " + u.getCognome()
                + ", la tua nuova Password è: \n" + newpassword;

        manageUsersService.updateUtentePassword(u, newpassword);
        emailService.sendEmail(email, subject, message);
        return "redirect:/admin/dettagliUtente?username=" + u.getUsername();
    }
}
