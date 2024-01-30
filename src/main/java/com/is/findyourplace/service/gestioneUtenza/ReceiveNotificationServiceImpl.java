package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.Notifica;
import com.is.findyourplace.persistence.entity.NotificaRicevuta;
import com.is.findyourplace.persistence.repository.NotificaRepository;
import com.is.findyourplace.persistence.repository.NotificaRicevutaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiveNotificationServiceImpl implements ReceiveNotificationService{
    private final NotificaRepository notificaRepository;
    private final NotificaRicevutaRepository notificaRicevutaRepository;

    public ReceiveNotificationServiceImpl(NotificaRepository notificaRepository, NotificaRicevutaRepository notificaRicevutaRepository) {
        this.notificaRepository = notificaRepository;
        this.notificaRicevutaRepository = notificaRicevutaRepository;
    }


    @Override
    public List<NotificaRicevuta> findAllNotificheRicevuteByIdUtente(Long id) {
        return notificaRicevutaRepository.findByIdUtente(id);
    }

    @Override
    public NotificaDto findByIdNotifica(long id) {

        return mapToNotificaDto(notificaRepository.findByIdNotifica(id));
    }

    private NotificaDto mapToNotificaDto(final Notifica n) {
        NotificaDto notificaDto = new NotificaDto();
        notificaDto.setAutore(n.getAutore());
        notificaDto.setTesto(n.getTesto());
        notificaDto.setDataInvio(n.getDataInvio());
        notificaDto.setDataScadenza(n.getExpireDate());

        return notificaDto;
    }
}
