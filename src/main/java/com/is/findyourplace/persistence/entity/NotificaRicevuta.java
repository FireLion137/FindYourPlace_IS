package com.is.findyourplace.persistence.entity;

import com.is.findyourplace.persistence.entity.CompositeKeys.NotificaRicevutaKey;
import jakarta.persistence.*;
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
     * Id composto della Notifica Ricevuta
     */
    @EmbeddedId
    private NotificaRicevutaKey idNotificaRicevuta;

    @ManyToOne
    @MapsId("idUtente")
    @JoinColumn(name = "id_utente")
    Utente utente;

    @ManyToOne
    @MapsId("idNotifica")
    @JoinColumn(name = "id_notifica")
    Notifica notifica;

    /**
     * Indica se la Notifica è stata segnata come letta.<br>
     */
    @NotNull
    private boolean isRead;
}
