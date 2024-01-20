package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.Notifica;

import java.util.List;

public interface NotificationService {
    void saveNotifica(NotificaDto notificaDto);
    void saveNotificaBroadcast(NotificaDto notificaDto);
    Notifica findByIdNotifica(Long id_notifica);
    List<Notifica> findByAutore(String autore);
    boolean existsById(Long id_notifica);
}
