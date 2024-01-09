package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Preferenze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenzeRepository extends JpaRepository<Preferenze, Long> {
    Preferenze findByIdUtente(long id_utente);
}
