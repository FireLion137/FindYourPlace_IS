package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoDto;
import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Filtri;

import java.util.List;

public interface SearchService {
    Long saveRicerca(RicercaDto ricercaDto);
    void saveLuogoDto(LuogoDto luogoDto);
    List<LuogoDto> findLuoghiByIdRicerca(Long idRicerca);
    Filtri findFiltriByIdRicerca(Long idRicerca);
}
