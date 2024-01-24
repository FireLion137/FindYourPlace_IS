package com.is.findyourplace.controller.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import com.is.findyourplace.service.gestioneUtenza.CustomUserDetailsService;
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

//Gestisce le operazioni su un profilo Utente
@Controller
public class ProfileController {
    private final AccountService accountService;
    private final ProfileService profileService;

    public ProfileController(AccountService accountService, ProfileService profileService, CustomUserDetailsService customUserDetailsService) {
        this.accountService = accountService;
        this.profileService = profileService;
    }

    @GetMapping("/editProfile")
    public String ViewEditProfile(Model model){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken)
            return "redirect:/serverError";
        UtenteDto utente= accountService.findByUsername(auth.getName());
        model.addAttribute("utente", utente);
        return "account/editProfile";
    }

    @PostMapping("/editProfile")
    public String EditProfile(@Valid @ModelAttribute("utente") UtenteDto utenteDto,
                              BindingResult result,
                              Model model,
                              HttpServletRequest request){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        Utente userToUpdate= accountService.findByUsernameOrEmail(auth.getName());

        boolean isUsernameEqual= userToUpdate.getUsername().equals(utenteDto.getUsername());

        if(!isUsernameEqual &&
                accountService.existsByUsername(utenteDto.getUsername())){
            result.rejectValue("username", "null",
                    "Username già usato!");
        }

        if(!userToUpdate.getEmail().equals(utenteDto.getEmail()) &&
                accountService.existsByEmail(utenteDto.getEmail())){
            result.rejectValue("email", "null",
                    "Email già usata!");
        }

        if(result.hasErrors()){
            model.addAttribute("utente", utenteDto);
            return "account/editProfile";
        }
        utenteDto.setIdUtente(userToUpdate.getIdUtente());
        if(!userToUpdate.getIdUtente().equals(utenteDto.getIdUtente()))
            return "redirect:/serverError";

        profileService.updateUtente(utenteDto);
        if(!utenteDto.getPassword().isBlank() ||
                !isUsernameEqual) {
            try {
                request.logout();
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }

            /* Not Secure
            UserDetails userDetails= customUserDetailsService.loadUserByUsername(utenteDto.getUsername());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getPassword(), userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            */
        }

        return "account/editProfile";
    }
}
