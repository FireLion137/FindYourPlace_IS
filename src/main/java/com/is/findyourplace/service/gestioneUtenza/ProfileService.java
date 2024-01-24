package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Preferenze;
import com.is.findyourplace.persistence.entity.Utente;

public interface ProfileService {
    void updateUtente(UtenteDto utenteDto);
    Preferenze findPrefByUtente(Utente utente);
    Preferenze createPreferenze(Utente utente);
    void updatePreferenze(Preferenze preferenze);
}
