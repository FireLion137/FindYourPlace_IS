package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.NotificaRicevuta;
import com.is.findyourplace.persistence.entity.CompositeKeys.NotificaRicevutaKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificaRicevutaRepository
        extends JpaRepository<NotificaRicevuta, NotificaRicevutaKey> {
    /**
     * Query custom per recuperare la lista delle notifiche ricevute
     * di un determinato utente tramite l'id.
     * @param idUtente Id dell' Utente
     * @return Lista di notifiche ricevute di quell' Utente
     */
    @Query("SELECT n FROM NotificaRicevuta n "
            + "WHERE n.idNotificaRicevuta.idUtente=?1")
    List<NotificaRicevuta> findByIdUtente(Long idUtente);
}
