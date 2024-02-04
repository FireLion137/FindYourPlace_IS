package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoPreferitoDto;
import com.is.findyourplace.persistence.entity.Luogo;
import com.is.findyourplace.persistence.entity.Preferiti;
import com.is.findyourplace.persistence.entity.Utente;

import java.util.List;

public interface SavedPlacesService {
    /**
     * Salva un luogo tra i preferiti fi un utente.
     * @param utente Id dell' Utente
     * @param luogo Id del Luogo
     */
    void savePreferito(Utente utente, Luogo luogo);
    /**
     * Query per recuperare la lista dei luoghi
     * preferiti di un utente.
     * @param idUtente Id dell' Utente
     * @return Lista dei luoghi preferiti dell'utente
     */
    List<LuogoPreferitoDto> findLuoghiPreferitiDtoByIdUtente(Long idUtente);

    /**
     * Query per recuperare il luogo preferito
     * tramite il suo id e quello dell'utente.
     * @param idUtente Id dell' Utente
     * @param idLuogo Id del Luogo
     * @return Luogo preferito con gli id indicati
     */
    Preferiti findPreferito(Long idUtente, Long idLuogo);

    /**
     * Query per rimuovere un luogo dai preferiti.
     * @param luogoSalvato Luogo da rimuovere
     */
    void deletePreferito(Preferiti luogoSalvato);

    /**
     * Query per la gestione delle notifiche di
     * un luogo preferito.
     * @param preferito luogo preferito
     * @param notifiche campo che definisce se le notifiche sono attive
     */
    void updateNotPreferito(Preferiti preferito, boolean notifiche);

    /**
     * Query per recuperare il luogo tramite il suo id.
     * @param idLuogo Id del Luogo
     * @return Luogo con l'id indicato
     */
    Luogo findLuogoById(Long idLuogo);
}
