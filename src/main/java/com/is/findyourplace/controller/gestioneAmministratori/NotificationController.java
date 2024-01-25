package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.service.gestioneAmministratori.NotificationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.time.LocalDateTime;

//Gestisce l'invio delle notifiche da parte di un Amministratore
@Controller
public class NotificationController {
    /**
     * Service usato per gestire le notifiche
     * inviate da un amministratore.
     */
    private final NotificationService notificationService;


    /**
     * Costruttore del controller.
     * @param notificationService NotificationService
     */
    public NotificationController(
            final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Mapping pagina di successo invio notifica.
     * @param model Model
     * @return admin/notificaInviata.html
     */
    @GetMapping("admin/notificaInviata")
    public String sendNotForm(final Model model) {
        NotificaDto n = new NotificaDto();
        model.addAttribute("notifica", n);
        return "admin/notificaInviata";
    }

    /**
     * Mapping pagina per inviare notifica a un utente.
     * @param notificaDto NotificaDto
     * @return admin/notificaInviata.html
     */
    @PostMapping("/sendNotification")
    public String invioNot(
            @Valid @ModelAttribute
            ("notifica") final NotificaDto notificaDto) {

        notificaDto.setDataInvio(LocalDateTime.now());
        notificaDto.setDataScadenza(LocalDateTime.now().plusMonths(2));

        notificationService.saveNotifica(notificaDto);

        return "redirect:/admin/notificaInviata";
    }

    /**
     * Mapping pagina per inviare notifica broadcast.
     * @param notificaDto NotificaDto
     * @return admin/notificaInviata.html
     */
    @PostMapping("/sendNotificationAll")
    public String invioNotAll(
            @Valid @ModelAttribute("notifica")
            final NotificaDto notificaDto) {

        notificaDto.setDataInvio(LocalDateTime.now());
        notificaDto.setDataScadenza(LocalDateTime.now().plusMonths(2));

        notificationService.saveNotificaBroadcast(notificaDto);

        return "redirect:/admin/notificaInviata";
    }
}
