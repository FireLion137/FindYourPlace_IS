package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ManageUsersServiceImpl implements ManageUsersService{

    private final UtenteRepository utenteRepository;

    public ManageUsersServiceImpl(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }


    @Override
    public List<UtenteDto> findAllUtenti() {
        List<Utente> utenti = utenteRepository.findAll();
        return utenti.stream()
                .map(this::mapToUtenteDto)
                .collect(Collectors.toList());
    }

    @Override
    public Utente findByUsernameOrEmail(String username) {
        return utenteRepository.findByUsernameOrEmail(username, username);
    }

    private UtenteDto mapToUtenteDto(Utente u){
        UtenteDto utenteDto= new UtenteDto();
        utenteDto.setIdUtente(u.getIdUtente());
        utenteDto.setUsername(u.getUsername());
        utenteDto.setEmail(u.getEmail());
        utenteDto.setNome(u.getNome());
        utenteDto.setCognome(u.getCognome());

        //Da vedere se servono
        utenteDto.setNumeroTel(u.getNumeroTel());
        utenteDto.setDataNascita(u.getDataNascita());

        return utenteDto;
    }
}
