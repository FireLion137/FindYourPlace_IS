package com.is.findyourplace.persistence.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe Dto di Utente
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDto {
    /**
     * Id dell'utente
     */
    private long idUtente;

    /**
     * Username dell' Utente.<br>
     * Deve essere unico e rispettare il pattern.
     */
    @NotEmpty
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$")
    private String username;

    /**
     * Password dell' Utente.<br>
     * Il suo pattern prevede tra 8 e 50 caratteri, numeri,
     * lettere maiuscole, minuscole e caratteri speciali.
     */
    @NotEmpty
    @Size(min = 8, max = 50)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\[{}\\]:;',?*~$^\\-+=<>]).{8,50}$")
    private String password;

    /**
     * Email dell' Utente.<br>
     * Deve essere unica e rispettare il pattern.
     */
    @NotEmpty
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
    @NotEmpty
    @Past
    private LocalDate dataNascita;

    /**
     * Nome dell' Utente.<br>
     * Deve rispettare il pattern.
     */
    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp = "^(?=.{2,50}$)[A-Za-zÀ-ÿ]+([-,. '][A-Za-zÀ-ÿ]+)*$")
    private String nome;

    /**
     * Nome dell' Utente.<br>
     * Deve rispettare il pattern.
     */
    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp = "^(?=.{2,50}$)[A-Za-zÀ-ÿ]+([-,. '][A-Za-zÀ-ÿ]+)*$")
    private String cognome;
}
