package com.is.findyourplace.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe relativa alle Preferenze di un utente.<br>
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
    @OneToOne(cascade = CascadeType.ALL)
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
    @Column(name = "isStudente")
    private boolean studente;

    /**
     * Campo che definisce se l'utente è un genitore.
     */
    @Column(name = "isGenitore")
    private boolean genitore;

    @Override
    public String toString() {
        return "Preferenze{" +
                "idUtente=" + idUtente +
                ", notifiche=" + notifiche +
                ", studente=" + studente +
                ", genitore=" + genitore +
                '}';
    }
}
