package com.is.findyourplace.service.gestioneUtenza;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.NotificaRicevuta;

import java.util.List;

public interface ReceiveNotificationService {
    List<NotificaRicevuta> findAllNotificheRicevuteByIdUtente(Long id);
    NotificaDto findByIdNotifica(long id);
    NotificaRicevuta findByIdUtenteAndIdNotifica(long idUtente,long idNotifica);
    void setRead(NotificaRicevuta notificaRicevuta,boolean isRead);
}
