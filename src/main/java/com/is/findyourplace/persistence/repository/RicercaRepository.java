package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Ricerca;
import com.is.findyourplace.persistence.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RicercaRepository extends JpaRepository<Ricerca, Long> {
    Ricerca findByIdRicerca(long id_ricerca);
    List<Ricerca> findByUtente(Utente utente);
}
