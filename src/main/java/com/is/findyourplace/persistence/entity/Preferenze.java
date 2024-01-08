package com.is.findyourplace.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe relativa a un Utente registrato.<br>
 * I campi sono:
 *  id utente,
 *  notifiche (boolean),
 *  isStudente,
 *  isGenitore.
 *  @author Pietro Esposito
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preferenze {
    /**
     * Id dell'utente
     */
    @Id
    @NotNull
    @Column(name = "id_utente")
    private long idUtente;

    /**
     * La chiave primaria deriva dalla chiave dell' utente
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "id_utente")
    private Utente utente;

    /**
     * Campo che definisce se le notifiche sono attive.
     */
    @NotNull
    private boolean notifiche;

    /**
     * Campo che definisce se l'utente è uno studente.
     */
    private boolean isStudente;

    /**
     * Campo che definisce se l'utente è un genitore.
     */
    private boolean isGenitore;
}
