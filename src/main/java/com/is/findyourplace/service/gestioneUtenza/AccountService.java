package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;

public interface AccountService {
    /**
     * Crea un nuovo Utente.
     * @param utenteDto UtenteDto
     */
    void saveUtente(UtenteDto utenteDto);

    /**
     * Trova utente tramite username o email.
     * @param username Username dell' utente
     * @return Utente
     */
    Utente findByUsernameOrEmail(String username);

    /**
     * Trova utente, mappandolo in UtenteDto, tramite username.
     * @param username Username dell' utente
     * @return UtenteDto
     */
    UtenteDto findByUsername(String username);

    /**
     * Controlla se esiste un Utente tramite l' username.
     * @param username Username dell' utente
     * @return boolean
     */
    boolean existsByUsername(String username);

    /**
     * Controlla se esiste un Utente tramite l' email.
     * @param email Email dell' utente
     * @return boolean
     */
    boolean existsByEmail(String email);
}
