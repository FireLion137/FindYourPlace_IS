package com.is.findyourplace.controller.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.service.gestioneAmministratori.NotificationService;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//Gestisce l'invio delle notifiche da parte di un Amministratore
@Controller
public class NotificationController {
    /**
     * Service usato per gestire le notifiche
     * inviate da un amministratore.
     */
    private final NotificationService notificationService;
    /**
     * Service usato per gestire gli account degli utenti.
     */
    private final AccountService accountService;


    /**
     * Costruttore del controller.
     *
     * @param notificationService NotificationService
     * @param accountService AccountService
     */
    public NotificationController(
            final NotificationService notificationService,
            final AccountService accountService) {
        this.notificationService = notificationService;
        this.accountService = accountService;
    }


    /**
     * Mapping pagina per inviare notifica a un utente.
     * @param notificaDto NotificaDto
     * @param result Contiene gli errori
     * @return 201 CREATED / 400 BAD REQUEST
     */
    @PostMapping("/sendNotification")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> invioNot(
            @Valid @ModelAttribute("notifica")
            final NotificaDto notificaDto,
            final BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        notificaDto.setDataInvio(LocalDateTime.now());
        notificaDto.setDataScadenza(LocalDateTime.now().plusMonths(2));
        if (notificaDto.getDestinatario() == null
                || notificaDto.getDestinatario().isBlank()
                || !accountService.existsByUsername(
                        notificaDto.getDestinatario())) {
            result.rejectValue("destinatario", "null",
                    "Destinatario non valido!");
        }

        if (result.hasErrors()) {
            response.put("errors", result.getAllErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        notificationService.saveNotifica(notificaDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Mapping pagina per inviare notifica broadcast.
     * @param notificaDto NotificaDto
     * @param result Contiene gli errori
     * @return 201 CREATED / 400 BAD REQUEST
     */
    @PostMapping("/sendNotificationAll")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> invioNotAll(
            @Valid @ModelAttribute("notifica")
            final NotificaDto notificaDto,
            final BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        notificaDto.setDataInvio(LocalDateTime.now());
        notificaDto.setDataScadenza(LocalDateTime.now().plusMonths(2));

        if (result.hasErrors()) {
            response.put("errors", result.getAllErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        notificationService.saveNotificaBroadcast(notificaDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
