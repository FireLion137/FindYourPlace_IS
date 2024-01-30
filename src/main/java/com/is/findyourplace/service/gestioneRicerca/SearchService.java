package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoDto;
import com.is.findyourplace.persistence.dto.RicercaDto;

public interface SearchService {
    Long saveRicerca(RicercaDto ricercaDto);
    void saveLuogoDto(LuogoDto luogoDto);
}
