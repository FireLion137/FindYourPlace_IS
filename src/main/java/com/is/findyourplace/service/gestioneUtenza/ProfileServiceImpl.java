package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Preferenze;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.PreferenzeRepository;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    /**
     * Repository Utente.
     */
    private final UtenteRepository utenteRepository;
    /**
     * Password Encoder.
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * Repository Preferenze.
     */
    private final PreferenzeRepository preferenzeRepository;

    /**
     * Costruttore del service.
     * @param utenteRepository UtenteRepository
     * @param passwordEncoder PasswordEncoder
     * @param preferenzeRepository PreferenzeRepository
     */
    public ProfileServiceImpl(
            final UtenteRepository utenteRepository,
            final PasswordEncoder passwordEncoder,
            final PreferenzeRepository preferenzeRepository) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.preferenzeRepository = preferenzeRepository;
    }

    @Override
    public void updateUtente(final UtenteDto utenteDto) {
        Utente utente = utenteRepository.findByIdUtente(
                utenteDto.getIdUtente()
        );

        utente.setUsername(utenteDto.getUsername());
        utente.setEmail(utenteDto.getEmail());

        //Encrypt using springboot security
        if (!utenteDto.getPassword().isBlank()) {
            utente.setPasswordHash(
                    passwordEncoder.encode(utenteDto.getPassword())
            );
        }

        if (utenteDto.getNumeroTel() != null) {
            utente.setNumeroTel(utenteDto.getNumeroTel());
        }
        utente.setDataNascita(utenteDto.getDataNascita());
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());

        utenteRepository.save(utente);
    }

    @Override
    public Preferenze findPrefByUtente(final Utente utente) {
        return preferenzeRepository.findByIdUtente(utente.getIdUtente());
    }

    @Override
    public Preferenze createPreferenze(final Utente utente) {
        Preferenze preferenze = new Preferenze();
        preferenze.setIdUtente(utente.getIdUtente());
        preferenze.setNotifiche(true);
        preferenze.setUtente(utente);
        utente.setPreferenze(preferenze);

        preferenzeRepository.save(preferenze);
        return preferenze;
    }

    @Override
    public void updatePreferenze(final Preferenze preferenze) {
        preferenzeRepository.save(preferenze);

        Utente utente = utenteRepository.findByIdUtente(
                preferenze.getIdUtente()
        );
        utente.setPreferenze(preferenze);
    }
}
