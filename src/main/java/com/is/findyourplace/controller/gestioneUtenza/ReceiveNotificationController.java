package com.is.findyourplace.controller.gestioneUtenza;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.NotificaRicevuta;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import com.is.findyourplace.service.gestioneUtenza.ReceiveNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReceiveNotificationController {
    /**
     * Service usato per gestire le notifiche ricevute.
     */
    private final ReceiveNotificationService receiveNotificationService;
    /**
     * Service usato per gestire gli account degli utenti.
     */
    private final AccountService accountService;

    /**
     * Costruttore del controller.
     * @param receiveNotificationService ReceiveNotificationService
     * @param accountService AccountService
     */
    public ReceiveNotificationController(
            final ReceiveNotificationService receiveNotificationService,
            final AccountService accountService) {
        this.receiveNotificationService = receiveNotificationService;
        this.accountService = accountService;
    }

    /**
     * Pagina per recuperare le notifiche.
     * @return 200 OK / 401 UNAUTHORIZED
     */
    @PostMapping("/retrieveNot")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getNotifiche() {
        Map<String, Object> response = new HashMap<>();

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            Long idUtente = accountService
                    .findByUsernameOrEmail(auth.getName())
                    .getIdUtente();
            List<NotificaDto> notifiche = new ArrayList<>();
            for (NotificaRicevuta n: receiveNotificationService
                            .findAllNotificheRicevuteByIdUtente(idUtente)) {
                notifiche.add(
                        receiveNotificationService
                                .findByIdNotifica(
                                        n.getIdNotificaRicevuta()
                                                .getIdNotifica()
                                )
                );
            }
            response.put("notifiche", notifiche);
        } else {
          return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Pagina per impostare una notifica come letta.
     * @param idNotifica Id della notifica.
     * @return 200 OK / 401 UNAUTHORIZED
     */
    @PostMapping("/isReadNot")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> isReadNot(
            @RequestParam final Long idNotifica) {
        Map<String, Object> response = new HashMap<>();

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            Long idUtente = accountService.findByUsernameOrEmail(
                    auth.getName()).getIdUtente();
            NotificaRicevuta notificaRicevuta =
                    receiveNotificationService.findByIdUtenteAndIdNotifica(
                            idUtente,
                            idNotifica
                    );
            receiveNotificationService.setRead(notificaRicevuta, true);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
