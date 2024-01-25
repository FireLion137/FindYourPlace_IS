package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.CompositeKeys.PreferitiKey;
import com.is.findyourplace.persistence.entity.Preferiti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferitiRepository
        extends JpaRepository<Preferiti, PreferitiKey> {
    /**
     * Query custom per recuperare la lista dei luoghi preferiti
     * di un determinato utente tramite l'id.
     * @param idUtente Id dell' Utente
     * @return Lista di notifiche ricevute di quell' Utente
     */
    @Query("SELECT p FROM Preferiti p WHERE p.idPreferiti.idUtente=?1")
    List<Preferiti> findByIdUtente(Long idUtente);
}
