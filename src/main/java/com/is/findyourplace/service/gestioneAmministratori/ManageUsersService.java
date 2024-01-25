package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;

import java.util.List;


public interface ManageUsersService {
    /**
     * Trova utente, mappandolo in UtenteDto, tramite username o email.
     * @param username Username dell' utente
     * @return UtenteDto
     */
    UtenteDto findByUsernameOrEmail(String username);

    /**
     * Restituisce la lista di tutti gli utenti, mappati in UtenteDto.
     * @return Lista di utenti
     */
    List<UtenteDto> findAllUtenti();

    /**
     * Aggiorna l'username di un utente.
     * @param utente Utente
     */
    void updateUtenteUsername(Utente utente);

    /**
     * Aggiorna l'username di un utente.
     * @param utente Utente
     * @param password Nuova password
     */
    void updateUtentePassword(Utente utente, String password);
}
