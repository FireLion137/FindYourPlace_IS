package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.service.gestioneAmministratori.ManageUsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

/**
 * Gestisce la visualizzazione degli utenti
 * da parte di un Amministratore.
 */
@Controller
public class ManageUsersController {
    /**
     * Service usato per gestire gli utenti.
     */
    private final ManageUsersService manageUsersService;

    /**
     * Costruttore del controller.
     * @param manageUsersService ManageUsersService
     */
    public ManageUsersController(final ManageUsersService manageUsersService) {
        this.manageUsersService = manageUsersService;
    }

    /**
     * Mapping pagina per visualizzare gli utenti.
     * @param model Model
     * @return admin/users.html
     */
    @GetMapping(value = {"/admin/users", "/admin/utenti"})
    public String utenti(final Model model) {
        List<UtenteDto> utenti = manageUsersService.findAllUtenti();
        NotificaDto n = new NotificaDto();
        model.addAttribute("utenti", utenti);
        model.addAttribute("notifica", n);
        return "admin/users";
    }
}
