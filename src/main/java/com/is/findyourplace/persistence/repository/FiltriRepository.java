package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Filtri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiltriRepository extends JpaRepository<Filtri, Long> {
    /**
     * Query per trovare i Filtri usando idRicerca.
     * @param idRicerca Id della Ricerca
     * @return Filtri della Ricerca
     */
    Filtri findByIdRicerca(long idRicerca);
}
