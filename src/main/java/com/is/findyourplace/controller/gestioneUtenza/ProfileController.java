package com.is.findyourplace.controller.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Preferenze;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import com.is.findyourplace.service.gestioneUtenza.ProfileService;
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
 * Gestisce le operazioni su un profilo Utente.
 */
@Controller
public class ProfileController {
    /**
     * Service usato per l'autenticazione.
     */
    private final AccountService accountService;
    /**
     * Service usato per la modifica del profilo.
     */
    private final ProfileService profileService;

    /**
     * Costruttore del controller.
     * @param accountService AccountService
     * @param profileService ProfileService
     */
    public ProfileController(
            final AccountService accountService,
            final ProfileService profileService) {
        this.accountService = accountService;
        this.profileService = profileService;
    }

    /**
     * Mapping della pagina per modificare il profilo.
     * @param model Model
     * @return account/editProfile.html
     */
    @GetMapping("/editProfile")
    public String viewEditProfile(final Model model) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/serverError";
        }
        UtenteDto utente = accountService.findByUsername(auth.getName());
        model.addAttribute("utente", utente);
        return "account/editProfile";
    }

    /**
     * Mapping per la richiesta di modifica profilo.
     * @param utenteDto UtenteDto contenente tutti i dati.
     * @param result BindingResult, contiene gli errori.
     * @param model Model
     * @param request HttpServletRequest
     * @return account/editProfile.html
     */
    @PostMapping("/editProfile")
    public String editProfile(
            @Valid @ModelAttribute("utente") final UtenteDto utenteDto,
            final BindingResult result,
            final Model model,
            final HttpServletRequest request) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        Utente userToUpdate =
                accountService.findByUsernameOrEmail(auth.getName());
        boolean isUsernameEqual =
                userToUpdate.getUsername().equals(utenteDto.getUsername());

        if (!isUsernameEqual
                && accountService.existsByUsername(
                        utenteDto.getUsername())) {
            result.rejectValue("username", "null",
                    "Username già usato!");
        }

        if (!userToUpdate.getEmail().equals(utenteDto.getEmail())
                && accountService.existsByEmail(
                        utenteDto.getEmail())) {
            result.rejectValue("email", "null",
                    "Email già usata!");
        }

        if (result.hasErrors()) {
            model.addAttribute("utente", utenteDto);
            return "account/editProfile";
        }
        utenteDto.setIdUtente(userToUpdate.getIdUtente());
        if (!userToUpdate.getIdUtente().equals(utenteDto.getIdUtente())) {
            return "redirect:/serverError";
        }

        profileService.updateUtente(utenteDto);
        if (!utenteDto.getPassword().isBlank()
                || !isUsernameEqual) {
            try {
                request.logout();
                return "redirect:/accountAuth";
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }

            /* Not Secure
            UserDetails userDetails=
                    customUserDetailsService.loadUserByUsername(
                            utenteDto.getUsername()
                    );
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authenticationToken);
            */
        }

        return "account/editProfile";
    }

    /**
     * Mapping della pagina per modificare le preferenze.
     * @param model Model
     * @return account/editPreferences.html
     */
    @GetMapping("/editPreferences")
    public String viewEditPreferences(final Model model) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth == null
                || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/serverError";
        }

        Utente utente = accountService.findByUsernameOrEmail(auth.getName());
        Preferenze preferenze = profileService.findPrefByUtente(utente);
        if (preferenze == null) {
            preferenze = profileService.createPreferenze(utente);
        }

        model.addAttribute("preferenze", preferenze);
        return "account/editPreferences";
    }

    /**
     * Mapping per la richiesta di modifica preferenze.
     * @param preferenze Preferenze
     * @param result BindingResult, contiene gli errori.
     * @param model Model
     * @return account/editProfile.html
     */
    @PostMapping("/editPreferences")
    public String editPreferences(
            @Valid @ModelAttribute("preferenze") final Preferenze preferenze,
            final BindingResult result,
            final Model model) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        Utente user = accountService.findByUsernameOrEmail(auth.getName());

        preferenze.setIdUtente(user.getIdUtente());
        if (!user.getIdUtente().equals(preferenze.getIdUtente())) {
            return "redirect:/serverError";
        }

        if (result.hasErrors()) {
            model.addAttribute("preferenze", preferenze);
            return "account/editPreferences";
        }

        profileService.updatePreferenze(preferenze);
        return "account/editPreferences";
    }
}
