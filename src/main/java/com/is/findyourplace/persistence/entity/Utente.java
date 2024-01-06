package com.is.findyourplace.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe relativa a un Utente registrato.<br>
 * I campi sono:
 *  id autogenerato,
 *  username,
 *  password crittografata,
 *  email,
 *  numero di telefono,
 *  data di nascita,
 *  boolean isAdmin,
 *  nome,
 *  cognome.
 *  @author Pietro Esposito
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {
    /**
     * Id dell'utente
     */
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_utente;

    /**
     * Username dell' Utente.<br>
     * Deve essere unico e rispettare il pattern.
     */
    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$")
    private String username;

    /**
     * Password crittografata dell' Utente.<br>
     * Il suo pattern prevede tra 8 e 50 caratteri, numeri,
     * lettere maiuscole, minuscole e caratteri speciali.
     */
    @NotNull
    private String passwordHash;

    /**
     * Email dell' Utente.<br>
     * Deve essere unica e rispettare il pattern.
     */
    @NotNull
    @Column(unique = true)
    @Size(max = 50)
    @Pattern(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    private String email;

    /**
     * Numero di telefono dell' Utente.<br>
     * Può essere Null e deve rispettare il pattern.
     */
    @Size(max = 15)
    @Pattern(regexp = "^([+]?[(]?[0-9]{1,3}[)]?[-\\s])?([(]?[0-9]{3}[)]?[-\\s]?)?([0-9][-\\s]?){3,10}[0-9]$")
    private String numeroTel;

    /**
     * Data di nascita dell' Utente.<br>
     */
    @NotNull
    @Past
    private LocalDate dataNascita;

    /**
     * Indica se un Utente è anche un Amministratore.
     */
    @NotNull
    private boolean isAdmin;

    /**
     * Nome dell' Utente.<br>
     * Deve rispettare il pattern.
     */
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^(?=.{2,50}$)[A-Za-zÀ-ÿ]+([-,. '][A-Za-zÀ-ÿ]+)*$")
    private String nome;

    /**
     * Nome dell' Utente.<br>
     * Deve rispettare il pattern.
     */
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^(?=.{2,50}$)[A-Za-zÀ-ÿ]+([-,. '][A-Za-zÀ-ÿ]+)*$")
    private String cognome;
}
