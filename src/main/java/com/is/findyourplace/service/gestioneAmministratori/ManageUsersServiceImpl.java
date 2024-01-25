package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ManageUsersServiceImpl implements ManageUsersService {
    /**
     * Repository Utente.
     */
    private final UtenteRepository utenteRepository;
    /**
     * Password Encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Costruttore del service.
     * @param utenteRepository UtenteRepository
     * @param passwordEncoder PasswordEncoder
     */
    public ManageUsersServiceImpl(
            final UtenteRepository utenteRepository,
            final PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void updateUtenteUsername(final Utente utente) {
        if (utenteRepository.existsByUsername(utente.getUsername())) {
            return;
        }
        if (!utenteRepository.existsByIdUtente(utente.getIdUtente())) {
            return;
        }
        utenteRepository.save(utente);
    }
    @Override
    public void updateUtentePassword(
            final Utente utente,
            final String password) {
        utente.setPasswordHash(passwordEncoder.encode(password));
        utenteRepository.save(utente);
    }
    @Override
    public List<UtenteDto> findAllUtenti() {
        List<Utente> utenti = utenteRepository.findAll();
        return utenti.stream()
                .map(this::mapToUtenteDto)
                .collect(Collectors.toList());
    }

    @Override
    public UtenteDto findByUsernameOrEmail(final String username) {
        Utente u = utenteRepository.findByUsernameOrEmail(username, username);
        return mapToUtenteDto(u);
    }

    private UtenteDto mapToUtenteDto(final Utente u) {
        UtenteDto utenteDto = new UtenteDto();
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
