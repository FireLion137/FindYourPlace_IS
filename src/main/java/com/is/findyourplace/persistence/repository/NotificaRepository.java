package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Notifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica, Long> {
    /**
     * Query per trovare una Notifica usando il suo id.
     * @param idNotifica Id della Notifica
     * @return Notifica
     */
    Notifica findByIdNotifica(Long idNotifica);
    /**
     * Query per trovare la lista notifiche inviate da un autore.
     * @param autore Autore della notifica
     * @return Lista di notifiche
     */
    List<Notifica> findByAutore(String autore);
    /**
     * Query per controllare se esiste una notifica tramite l'id.
     * @param idNotifica Id della notifica
     * @return boolean
     */
    boolean existsByIdNotifica(long idNotifica);

    /**
     * Query per trovare la notifica più recente
     * tramite autore e testo (usato per i test).
     * @param autore Autore della notifica
     * @param testo Testo della notifica
     * @return Notifica
     */
    Notifica findFirstByAutoreAndTestoOrderByDataInvioDesc(
            String autore,
            String testo
    );

    /**
     * Query per controllare se esiste una notifica
     * con autore e testo specificati con la data di scadenza passata
     * @param autore Autore della notifica
     * @param testo Testo della notifica
     * @param localDateTime Data oltre cui non bisogna andare
     * @return boolean
     */
    boolean existsByAutoreAndTestoAndExpireDateAfter(
            String autore,
            String testo,
            LocalDateTime localDateTime
    );
}
