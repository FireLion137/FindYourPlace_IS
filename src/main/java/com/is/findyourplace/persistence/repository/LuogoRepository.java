package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Luogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LuogoRepository extends JpaRepository<Luogo, Long> {
    /**
     * Query per trovare un Luogo usando il suo id.
     * @param idLuogo Id del Luogo
     * @return Luogo
     */
    Luogo findByIdLuogo(long idLuogo);

    boolean existsByNome(String nome);

    Luogo findByNome(String nome);
}
