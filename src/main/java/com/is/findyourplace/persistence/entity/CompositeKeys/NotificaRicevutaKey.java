package com.is.findyourplace.persistence.entity.CompositeKeys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Chiave primaria composta di NotificaRicevuta.
 * @author Pietro Esposito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class NotificaRicevutaKey implements Serializable {
    @NotNull
    @Column(name = "id_utente")
    private long idUtente;

    @NotNull
    @Column(name = "id_notifica")
    private long idNotifica;
}
