package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Notifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica, Long> {
    Notifica findByIdNotifica(Long id_notifica);
    List<Notifica> findByAutore(String autore);
}
