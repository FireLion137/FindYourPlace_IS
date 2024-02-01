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

    /**
     * Query per controllare se esiste un Luogo usando il suo nome.
     * @param nome Nome del Luogo
     * @return true/false
     */
    boolean existsByNome(String nome);

    /**
     * Query per trovare un Luogo usando il suo nome.
     * @param nome Nome del luogo
     * @return Luogo
     */
    Luogo findByNome(String nome);
}
