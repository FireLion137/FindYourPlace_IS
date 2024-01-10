package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final UtenteRepository utenteRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AccountServiceImpl(UtenteRepository utenteRepository, BCryptPasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUtente(UtenteDto utenteDto) {
        Utente utente= new Utente();
        utente.setUsername(utenteDto.getUsername());
        utente.setEmail(utenteDto.getEmail());

        //Encrypt using springboot security
        utente.setPasswordHash(passwordEncoder.encode(utenteDto.getPassword()));

        if(utenteDto.getNumeroTel() != null)
            utente.setNumeroTel(utenteDto.getNumeroTel());
        utente.setDataNascita(utenteDto.getDataNascita());
        utente.setAdmin(false);
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());

        utenteRepository.save(utente);
    }

    @Override
    public Utente findByUsernameOrEmail(String username) {
        return utenteRepository.findByUsernameOrEmail(username, username);
    }

    @Override
    public List<UtenteDto> findAllUtenti() {
        List<Utente> utenti = utenteRepository.findAll();
        return utenti.stream()
                .map(this::mapToUtenteDto)
                .collect(Collectors.toList());
    }

    private UtenteDto mapToUtenteDto(Utente u){
        UtenteDto utenteDto= new UtenteDto();
        utenteDto.setUsername(u.getUsername());
        utenteDto.setEmail(u.getEmail());
        utenteDto.setNome(u.getNome());
        utenteDto.setCognome(u.getNome());

        //Da vedere se servono
        utenteDto.setNumeroTel(u.getNumeroTel());
        utenteDto.setDataNascita(u.getDataNascita());

        return utenteDto;
    }
}
