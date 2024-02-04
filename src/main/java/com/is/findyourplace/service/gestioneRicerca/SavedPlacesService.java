package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoPreferitoDto;
import com.is.findyourplace.persistence.entity.Luogo;
import com.is.findyourplace.persistence.entity.Preferiti;
import com.is.findyourplace.persistence.entity.Utente;

import java.util.List;

public interface SavedPlacesService {
    void savePreferito(Utente utente, Luogo luogo);
    List<LuogoPreferitoDto> findLuoghiPreferitiDtoByIdUtente(Long idUtente);
    Preferiti findPreferito(Long idUtente, Long idLuogo);
    void deletePreferito(Preferiti luogoSalvato);
    void updateNotPreferito(Preferiti preferito, boolean notifiche);
    Luogo findLuogoById(Long idLuogo);
}
