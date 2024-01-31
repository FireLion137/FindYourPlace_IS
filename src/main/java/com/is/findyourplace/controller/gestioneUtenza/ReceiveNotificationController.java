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
    private final ReceiveNotificationService receiveNotificationService;
    private final AccountService accountService;

    public ReceiveNotificationController(ReceiveNotificationService receiveNotificationService, AccountService accountService) {
        this.receiveNotificationService = receiveNotificationService;
        this.accountService = accountService;
    }

    @PostMapping("/retrieveNot")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getNotifiche() {
        Map<String, Object> response = new HashMap<>();

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            Long idUtente=accountService.findByUsernameOrEmail(auth.getName()).getIdUtente();
            List<NotificaDto> notifiche= new ArrayList<>();
            for (NotificaRicevuta n:receiveNotificationService.findAllNotificheRicevuteByIdUtente(idUtente)){
                notifiche.add(receiveNotificationService.findByIdNotifica(n.getIdNotificaRicevuta().getIdNotifica()));
            }
            response.put("notifiche",notifiche);
        } else {
          return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/isReadNot")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> isReadNot(@RequestParam final Long idNotifica) {
        Map<String, Object> response = new HashMap<>();

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            Long idUtente=accountService.findByUsernameOrEmail(auth.getName()).getIdUtente();
            NotificaRicevuta notificaRicevuta= receiveNotificationService.findByIdUtenteAndIdNotifica(idUtente,idNotifica);
            receiveNotificationService.setRead(notificaRicevuta,true);
        } else {
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
