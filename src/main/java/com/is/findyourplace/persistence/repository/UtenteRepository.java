package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    /**
     * Query per trovare un Utente tramite Username o Email.
     * @param username Username dell' utente
     * @param email Email dell' utente
     * @return Utente
     */
    Utente findByUsernameOrEmail(String username, String email);

    /**
     * Query per controllare se esiste un Utente tramite l' id.
     * @param idUtente Id dell' utente
     * @return boolean
     */
    boolean existsByIdUtente(Long idUtente);
    /**
     * Query per controllare se esiste un Utente tramite l' username.
     * @param username Username dell' utente
     * @return boolean
     */
    boolean existsByUsername(String username);
    /**
     * Query per controllare se esiste un Utente tramite l' email.
     * @param email Email dell' utente
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * Query per trovare un Utente tramite l' username.
     * @param username Username dell' utente
     * @return Utente
     */
    Utente findByUsername(String username);
    /**
     * Query per trovare un Utente tramite l' email.
     * @param email Email dell' utente
     * @return Utente
     */
    Utente findByEmail(String email);
    /**
     * Query per trovare un Utente tramite l' id.
     * @param idUtente Id dell' utente
     * @return Utente
     */
    Utente findByIdUtente(Long idUtente);
}
