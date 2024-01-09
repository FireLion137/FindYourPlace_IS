package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.CompositeKeys.LuogoTrovatoKey;
import com.is.findyourplace.persistence.entity.LuogoTrovato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LuogoTrovatoRepository extends JpaRepository<LuogoTrovato, LuogoTrovatoKey> {
    /**
     * Query custom per recuperare la lista dei luoghi trovati
     * da una determinata ricerca tramite l'id.
     * @param id_ricerca Id della Ricerca
     * @return Lista di luoghi trovati in una ricerca.
     */
    @Query("SELECT l FROM LuogoTrovato l WHERE l.idLuogoTrovato.idRicerca=?1")
    List<LuogoTrovato> findByIdRicerca(Long id_ricerca);
}
