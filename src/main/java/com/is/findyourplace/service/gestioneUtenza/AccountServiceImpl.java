package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
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
    public UtenteDto findByUsername(String username) {
        return mapToUtenteDto(utenteRepository.findByUsername(username));
    }

    @Override
    public List<UtenteDto> findAllUtenti() {
        List<Utente> utenti = utenteRepository.findAll();
        return utenti.stream()
                .map(this::mapToUtenteDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUsername(String username) {
        return utenteRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return utenteRepository.existsByEmail(email);
    }


    private UtenteDto mapToUtenteDto(Utente u){
        UtenteDto utenteDto= new UtenteDto();
        utenteDto.setUsername(u.getUsername());
        utenteDto.setEmail(u.getEmail());
        utenteDto.setNome(u.getNome());
        utenteDto.setCognome(u.getCognome());
        utenteDto.setNumeroTel(u.getNumeroTel());
        utenteDto.setDataNascita(u.getDataNascita());

        return utenteDto;
    }
}
