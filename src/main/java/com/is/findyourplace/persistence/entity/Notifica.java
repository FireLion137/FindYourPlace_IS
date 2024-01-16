package com.is.findyourplace.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe relativa a una Notifica.<br>
 * I campi sono:
 *  id composto da utente e notifica,
 *  autore,
 *  testo,
 *  data e ora di invio,
 *  data e ora di scadenza.
 *  @author Pietro Esposito
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifica {
    /**
     * Id della Notifica
     */
    @Id
    @NotNull
    @Column(name = "id_notifica")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idNotifica;

    /**
     * Autore della Notifica.<br>
     * Deve rispettare il pattern.
     */
    @NotNull
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$")
    private String autore;

    /**
     * Testo della Notifica.<br>
     * Deve rispettare il pattern.
     */
    @NotNull
    @Size(max = 1000)
    @Pattern(regexp = "^[\\s\\S]{2,1000}$")
    private String testo;

    /**
     * Data e ora di invio della Notifica.
     */
    @NotNull
    @PastOrPresent
    private LocalDateTime dataInvio;

    /**
     * Data e ora di scadenza della Notifica.
     */
    @NotNull
    @Future
    private LocalDateTime expireDate;

    /**
     * Lista di chi ha ricevuto la notifica.
     */
    @OneToMany(mappedBy = "notifica", cascade = CascadeType.ALL)
    List<NotificaRicevuta> notificheRicevute;
}
