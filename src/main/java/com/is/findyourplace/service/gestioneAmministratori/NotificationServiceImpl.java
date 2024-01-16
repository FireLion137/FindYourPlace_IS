package com.is.findyourplace.service.gestioneAmministratori;

import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Notifica;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.NotificaRepository;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final UtenteRepository utenteRepository;

    private final NotificaRepository notificaRepository;


    public NotificationServiceImpl(UtenteRepository utenteRepository, NotificaRepository notificaRepository) {
        this.utenteRepository = utenteRepository;
        this.notificaRepository = notificaRepository;
    }

    @Override
    public void saveNotifica(NotificaDto notificaDto) {
        Notifica notifica= new Notifica();
        notifica.setAutore(notificaDto.getAutore());
        notifica.setTesto(notificaDto.getTesto());
        notifica.setDataInvio(notificaDto.getDataInvio());
        notifica.setExpireDate(notificaDto.getDataScadenza());

        notificaRepository.save(notifica);
    }
    @Override
    public Notifica findByIdNotifica(Long id_notifica) {
        return notificaRepository.findByIdNotifica(id_notifica);
    }


    @Override
    public List<Notifica> findByAutore(String autore) {
        return notificaRepository.findByAutore(autore);
    }

    @Override
    public boolean existsById(Long id_notifica) {
        return notificaRepository.existsById(id_notifica);
    }

    private NotificaDto mapToNotificaDto(Notifica n){
        NotificaDto notificaDto= new NotificaDto();
        notificaDto.setIdNotifica(n.getIdNotifica());
        notificaDto.setAutore(n.getAutore());
        notificaDto.setTesto(n.getTesto());
        notificaDto.setDataInvio(n.getDataInvio());
        notificaDto.setDataScadenza(n.getExpireDate());

        return notificaDto;
    }
}
