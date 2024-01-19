package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.service.gestioneAmministratori.ManageUsersService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserDetailsController {

    private final ManageUsersService manageUsersService;

    public UserDetailsController(ManageUsersService manageUsersService) {
        this.manageUsersService = manageUsersService;
    }

    @GetMapping("admin/dettagliUtente")
    public String dettUtente(Model model, @RequestParam String username) {
        UtenteDto utente = manageUsersService.findByUsernameOrEmail(username);
        NotificaDto n =new NotificaDto();
        model.addAttribute("utente",utente );
        model.addAttribute("notifica",n);
        return "admin/dettagliUtente";
    }
    @PostMapping("/modify")
    public String registration(@Valid @ModelAttribute("utente") UtenteDto utenteDto,
                               BindingResult result,
                               Model model) {

        return "redirect:/admin/dettagliUtente";
    }

}
