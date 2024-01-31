package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoDto;
import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Filtri;

import java.util.List;

public interface SearchService {
    /**
     * Salva una Ricerca.
     * @param ricercaDto RicercaDto
     * @return Id della ricerca salvata
     */
    Long saveRicerca(RicercaDto ricercaDto);

    /**
     * Salva un Luogo.
     * @param luogoDto LuogoDto
     */
    void saveLuogoDto(LuogoDto luogoDto);

    /**
     * Trova lista di luoghi Dto tramite idRicerca.
     * @param idRicerca Id della ricerca
     * @return Lista di Luoghi Dto
     */
    List<LuogoDto> findLuoghiByIdRicerca(Long idRicerca);

    /**
     * Trova i Filtri di una Ricerca.
     * @param idRicerca Id della Ricerca
     * @return Filtri
     */
    Filtri findFiltriByIdRicerca(Long idRicerca);
}
