package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Notifica;
import com.is.findyourplace.service.gestioneAmministratori.ManageUsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

//Gestisce la visualizzazione e modifica parametri degli utenti da parte di un Amministratore
@Controller
public class ManageUsersController {

    private final ManageUsersService manageUsersService;

    public ManageUsersController(ManageUsersService manageUsersService) {
        this.manageUsersService = manageUsersService;
    }

    @GetMapping(value = {"/admin/users", "/admin/utenti"})
    public String utenti(Model model) {
        List<UtenteDto> utenti= manageUsersService.findAllUtenti();
        NotificaDto n =new NotificaDto();
        model.addAttribute("utenti", utenti);
        model.addAttribute("notifica",n);
        return "admin/users";
    }




}
