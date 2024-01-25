package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Preferenze;
import com.is.findyourplace.persistence.entity.Utente;

public interface ProfileService {
    /**
     * Aggiorna dati Utente.
     * @param utenteDto UtenteDto
     */
    void updateUtente(UtenteDto utenteDto);

    /**
     * Trova Preferenze di un Utente.
     * @param utente Utente
     * @return Preferenze
     */
    Preferenze findPrefByUtente(Utente utente);

    /**
     * Crea Preferenze se un Utente non le possiede.
     * @param utente Utente
     * @return Preferenze
     */
    Preferenze createPreferenze(Utente utente);

    /**
     * Aggiorna dati Preferenze.
     * @param preferenze preferenze
     */
    void updatePreferenze(Preferenze preferenze);
}
