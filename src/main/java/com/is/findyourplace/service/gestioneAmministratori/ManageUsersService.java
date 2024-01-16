package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;

import java.util.List;


public interface ManageUsersService {
    Utente findByUsernameOrEmail(String username);
    List<UtenteDto> findAllUtenti();

}
