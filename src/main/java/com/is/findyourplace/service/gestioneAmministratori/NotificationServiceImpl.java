package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.Notifica;
import com.is.findyourplace.persistence.entity.NotificaRicevuta;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.NotificaRepository;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    /**
     * Repository Utente.
     */
    private final UtenteRepository utenteRepository;
    /**
     * Repository Notifica.
     */
    private final NotificaRepository notificaRepository;

    /**
     * Costruttore del service.
     * @param utenteRepository UtenteRepository
     * @param notificaRepository NotificaRepository
     */
    public NotificationServiceImpl(
            final UtenteRepository utenteRepository,
            final NotificaRepository notificaRepository) {
        this.utenteRepository = utenteRepository;
        this.notificaRepository = notificaRepository;
    }

    @Override
    @Transactional
    public void saveNotifica(final NotificaDto notificaDto) {
        Notifica notifica = new Notifica();
        notifica.setAutore(notificaDto.getAutore());
        notifica.setTesto(notificaDto.getTesto());
        notifica.setDataInvio(notificaDto.getDataInvio());
        notifica.setExpireDate(notificaDto.getDataScadenza());

        notificaRepository.save(notifica);

        Utente utente = utenteRepository.findByUsername(
                notificaDto.getDestinatario()
        );
        NotificaRicevuta notificaRicevuta =
                new NotificaRicevuta(utente, notifica);
        utente.getNotificheRicevute().add(notificaRicevuta);
        notifica.getNotificheRicevute().add(notificaRicevuta);
    }

    @Override
    @Transactional
    public void saveNotificaBroadcast(final NotificaDto notificaDto) {
        Notifica notifica = new Notifica();
        notifica.setAutore(notificaDto.getAutore());
        notifica.setTesto(notificaDto.getTesto());
        notifica.setDataInvio(notificaDto.getDataInvio());
        notifica.setExpireDate(notificaDto.getDataScadenza());

        notificaRepository.save(notifica);

        for (Utente utente: utenteRepository.findAll()) {
            NotificaRicevuta notificaRicevuta =
                    new NotificaRicevuta(utente, notifica);
            utente.getNotificheRicevute().add(notificaRicevuta);
            notifica.getNotificheRicevute().add(notificaRicevuta);
        }
    }

    @Override
    public Notifica findByIdNotifica(final Long idNotifica) {
        return notificaRepository.findByIdNotifica(idNotifica);
    }


    @Override
    public List<Notifica> findByAutore(final String autore) {
        return notificaRepository.findByAutore(autore);
    }

    @Override
    public boolean existsByIdNotifica(final Long idNotifica) {
        return notificaRepository.existsByIdNotifica(idNotifica);
    }

    private NotificaDto mapToNotificaDto(final Notifica n) {
        NotificaDto notificaDto = new NotificaDto();
        notificaDto.setIdNotifica(n.getIdNotifica());
        notificaDto.setAutore(n.getAutore());
        notificaDto.setTesto(n.getTesto());
        notificaDto.setDataInvio(n.getDataInvio());
        notificaDto.setDataScadenza(n.getExpireDate());

        return notificaDto;
    }
}
