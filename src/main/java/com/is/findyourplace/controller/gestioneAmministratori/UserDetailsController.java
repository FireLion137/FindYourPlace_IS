package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.service.gestioneAmministratori.ManageUsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserDetailsController {

    private final ManageUsersService manageUsersService;

    public UserDetailsController(ManageUsersService manageUsersService) {
        this.manageUsersService = manageUsersService;
    }

    @GetMapping("admin/dettagliUtente")
    public String dettUtente(Model model) {
        UtenteDto u = new UtenteDto();
        model.addAttribute("utente", u);
        return "admin/dettagliUtente";
    }


}
