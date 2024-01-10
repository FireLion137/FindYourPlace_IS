package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.controller.gestioneUtenza.CustomUserDetails;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UtenteRepository utenteRepository;
    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByUsernameOrEmail(username, username);

        if (utente != null) {
            return CustomUserDetails.fromUtente(utente);
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}