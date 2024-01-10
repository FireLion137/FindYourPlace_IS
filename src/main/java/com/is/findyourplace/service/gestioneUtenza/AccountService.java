package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;

import java.util.List;

public interface AccountService {
    void saveUtente(UtenteDto utenteDto);
    Utente findByUsernameOrEmail(String username);
    List<UtenteDto> findAllUtenti();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
