package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Preferenze;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void updateUtente(UtenteDto utenteDto) {
        Utente utente= utenteRepository.findByIdUtente(utenteDto.getIdUtente());

        utente.setUsername(utenteDto.getUsername());
        utente.setEmail(utenteDto.getEmail());

        //Encrypt using springboot security
        if(!utenteDto.getPassword().isBlank())
            utente.setPasswordHash(passwordEncoder.encode(utenteDto.getPassword()));

        if(utenteDto.getNumeroTel() != null)
            utente.setNumeroTel(utenteDto.getNumeroTel());
        utente.setDataNascita(utenteDto.getDataNascita());
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());

        utenteRepository.save(utente);
    }

    @Override
    public void createPreferenze(UtenteDto utenteDto) {

    }

    @Override
    public void updatePreferenze(Preferenze preferenze) {

    }
}
