package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Ricerca;

import java.util.List;

public interface HistoryService {
    /**
     * Query per recuperare la cronologia
     * delle ricerche di un determinato utente
     * tramite il suo id.
     * @param idUtente Id dell' Utente
     * @return Lista di Ricerche fatte da quell' Utente
     */
    List<RicercaDto> findRicercheDtoByIdUtente(Long idUtente);

    /**
     * Query per recuperare una ricerca tramite
     * l'id dell'utente e l'id della ricerca.
     * @param idRicerca Id della Ricerca
     * @param idUtente Id dell' Utente
     * @return Ricerca con gli id indicati
     */
    Ricerca findRicerca(Long idRicerca, Long idUtente);

    /**
     * Query per rimuovere l'id di un utente da
     * una ricerca.
     * @param ricerca ogetto Ricerca
     */
    void removeIdUtente(Ricerca ricerca);
}
