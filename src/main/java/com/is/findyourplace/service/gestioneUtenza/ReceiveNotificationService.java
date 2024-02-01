package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.NotificaRicevuta;

import java.util.List;

public interface ReceiveNotificationService {
    /**
     * Trova Lista di Notifiche Ricevute di un utente.
     * @param id Id dell' Utente
     * @return Lista di NotificheRicevute
     */
    List<NotificaRicevuta> findAllNotificheRicevuteByIdUtente(Long id);

    /**
     * Trova NotificaDto tramite il suo id.
     * @param id Id della Notifica
     * @return NotificaDto
     */
    NotificaDto findByIdNotifica(long id);

    /**
     * Trova NotificaRicevuta tramite l'Id di un utente e l'Id della notifica.
     * @param idUtente Id dell' Utente
     * @param idNotifica Id della Notifica
     * @return NotificaRicevuta
     */
    NotificaRicevuta findByIdUtenteAndIdNotifica(
            long idUtente,
            long idNotifica);

    /**
     * Cambia il valore che indica se una notifica è stata letta.
     * @param notificaRicevuta NotificaRicevuta
     * @param isRead true/false
     */
    void setRead(NotificaRicevuta notificaRicevuta, boolean isRead);
}
