package com.is.findyourplace.persistence.entity;

import com.is.findyourplace.persistence.entity.CompositeKeys.NotificaRicevutaKey;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MapsId;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe relativa a una Notifica Ricevuta da un Utente.<br>
 * I campi sono:
 *  id utente,
 *  id notifica,
 *  isRead.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificaRicevuta {
    /**
     * Id composto della Notifica Ricevuta.
     */
    @EmbeddedId
    private NotificaRicevutaKey idNotificaRicevuta;

    /**
     * Ogni NotificaRicevuta è collegata a un Utente.
     */
    @ManyToOne
    @MapsId("idUtente")
    @JoinColumn(name = "id_utente")
    Utente utente;

    /**
     * Ogni NotificaRicevuta è collegata a una Notifica.
     */
    @ManyToOne
    @MapsId("idNotifica")
    @JoinColumn(name = "id_notifica")
    Notifica notifica;

    /**
     * Indica se la Notifica è stata segnata come letta.<br>
     */
    @NotNull
    private boolean isRead;

    /**
     * Costruttore NotificaRicevuta.
     * @param utente Utente che ha ricevuto la Notifica
     * @param notifica Notifica
     */
    public NotificaRicevuta(final Utente utente, final Notifica notifica) {
        this.utente = utente;
        this.notifica = notifica;
        this.idNotificaRicevuta = new NotificaRicevutaKey(
                utente.getIdUtente(),
                notifica.getIdNotifica()
        );
    }
}
