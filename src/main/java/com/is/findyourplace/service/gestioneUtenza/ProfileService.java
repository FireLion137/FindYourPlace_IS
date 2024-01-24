package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Preferenze;

public interface ProfileService {
    void updateUtente(UtenteDto utenteDto);
    void createPreferenze(UtenteDto utenteDto);
    void updatePreferenze(Preferenze preferenze);
}
