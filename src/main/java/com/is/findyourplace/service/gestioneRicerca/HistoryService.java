package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Ricerca;

import java.util.List;

public interface HistoryService {
    List<RicercaDto> findRicercheDtoByIdUtente(Long idUtente);
    Ricerca findRicerca(Long idRicerca, Long idUtente);
    void removeIdUtente(Ricerca ricerca);
}
