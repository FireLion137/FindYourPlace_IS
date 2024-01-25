package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Ricerca;
import com.is.findyourplace.persistence.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RicercaRepository extends JpaRepository<Ricerca, Long> {
    /**
     * Query per trovare una Ricerca tramite il suo id.
     * @param idRicerca Id della Ricerca
     * @return Ricerca
     */
    Ricerca findByIdRicerca(long idRicerca);
    /**
     * Query per trovare la lista delle ricerche di un utente.
     * @param utente Utente
     * @return Lista di ricerche
     */
    List<Ricerca> findByUtente(Utente utente);
}
