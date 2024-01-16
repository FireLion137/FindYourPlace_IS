package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.service.gestioneAmministratori.ManageUsersService;
import com.is.findyourplace.service.gestioneAmministratori.NotificationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.time.LocalDateTime;

//Gestisce l'invio delle notifiche da parte di un Amministratore
@Controller
public class NotificationController {
    private final NotificationService notificationService;
    private final ManageUsersService manageUsersService;


    public NotificationController(NotificationService notificationService,ManageUsersService manageUsersService) {
        this.notificationService = notificationService;
        this.manageUsersService = manageUsersService;
    }

    @GetMapping("admin/notificaInviata")
    public String SendNotForm(Model model){
        NotificaDto n = new NotificaDto();
        model.addAttribute("notifica", n);
        return  "admin/notificaInviata";
    }

    @PostMapping("/sendNotification")
    public String invioNot(@Valid @ModelAttribute("notifica") NotificaDto notificaDto,
                               BindingResult result,
                               Model model) {

        notificaDto.setDataInvio(LocalDateTime.now());
        notificaDto.setDataScadenza(LocalDateTime.now().plusMonths(2));

        notificationService.saveNotifica(notificaDto);

        return "redirect:/admin/notificaInviata";
    }

    @PostMapping("/sendNotificationAll")
    public String invioNotAll(@Valid @ModelAttribute("notifica") NotificaDto notificaDto,
                           BindingResult result,
                           Model model) {

        notificaDto.setDataInvio(LocalDateTime.now());
        notificaDto.setDataScadenza(LocalDateTime.now().plusMonths(2));

        notificationService.saveNotifica(notificaDto);

        return "redirect:/admin/notificaInviata";
    }
}
