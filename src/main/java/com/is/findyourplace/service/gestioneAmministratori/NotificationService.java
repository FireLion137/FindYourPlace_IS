package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;

public interface NotificationService {
    /**
     * Salva notifica verso un singolo Utente.
     * @param notificaDto NotificaDto
     */
    void saveNotifica(NotificaDto notificaDto);
    /**
     * Salva notifica verso tutti gli utenti.
     * @param notificaDto NotificaDto
     */
    void saveNotificaBroadcast(NotificaDto notificaDto);
}
