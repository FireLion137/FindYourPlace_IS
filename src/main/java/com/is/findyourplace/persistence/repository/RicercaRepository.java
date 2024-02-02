package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Ricerca;
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
     * @param idUtente Id dell' Utente
     * @return Lista di ricerche
     */
    List<Ricerca> findByIdUtente(Long idUtente);
    /**
     * Query per trovare una Ricerca fatta da un utente.
     * @param idRicerca Id della Ricerca
     * @param idUtente Id dell' Utente
     * @return Ricerca
     */
    Ricerca findByIdRicercaAndIdUtente(long idRicerca, long idUtente);
}
