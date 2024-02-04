package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * Query personalizzata per restituire una lista di utenti
     * che hanno salvato un luogo e le cui notifiche sono attive,
     * in base a quanto sono distanti gli indici di qualità tra
     * il luogo salvato e il luogo in generale.
     * @param idLuogo Id del Luogo salvato dall'utente
     * @param idqDistance Distanza tra gli indici di qualità
     * @return Lista di utenti
     */
    @Query("SELECT u FROM Utente u "
            + "INNER JOIN Preferenze pref ON u.idUtente=pref.idUtente "
            + "INNER JOIN Preferiti p ON u.idUtente=p.idPreferiti.idUtente "
            + "INNER JOIN Luogo l ON p.idPreferiti.idLuogo = l.idLuogo "
            + "WHERE pref.notifiche=true AND p.notifiche=true "
            + "AND p.idPreferiti.idLuogo=?1 "
            + "AND ABS(p.qualityIndex - l.qualityIndex)>?2")
    List<Utente> findUtentiByIdLuogoPreferito(Long idLuogo, int idqDistance);
}
