package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Notifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
