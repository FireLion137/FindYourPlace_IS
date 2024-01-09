package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Luogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LuogoRepository extends JpaRepository<Luogo, Long> {
    Luogo findByIdLuogo(long id_luogo);
}
