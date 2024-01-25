package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.Notifica;

import java.util.List;

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

    /**
     * Trova Notifica tramite l'id.
     * @param idNotifica Id della Notifica
     * @return Notifica
     */
    Notifica findByIdNotifica(Long idNotifica);

    /**
     * Trova lista di notifiche inviate da un autore.
     * @param autore Autore delle notifiche
     * @return Lista di notifiche
     */
    List<Notifica> findByAutore(String autore);

    /**
     * Controlla se esiste una notifica tramite l'id.
     * @param idNotifica Id della Notifica
     * @return boolean
     */
    boolean existsByIdNotifica(Long idNotifica);
}
