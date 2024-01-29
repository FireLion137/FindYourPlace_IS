package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Luogo;

public interface SearchService {
    void saveRicerca(RicercaDto ricercaDto);
    void saveLuogo(Luogo luogo);
}
