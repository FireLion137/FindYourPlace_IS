package com.is.findyourplace.persistence.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe Dto di Utente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDto {
    /**
     * Id dell'utente.
     */
    private Long idUtente;

    /**
     * Username dell' Utente.<br>
     * Deve essere unico e rispettare il pattern.
     */
    @NotEmpty
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$",
            message = "L'Username non rispetta il formato corretto!")
    private String username;

    /**
     * Password dell' Utente.<br>
     * Il suo pattern prevede tra 8 e 50 caratteri, numeri,
     * lettere maiuscole, minuscole e caratteri speciali.
     */
    @Size(max = 50)
    @Pattern(
            regexp = "^$|"
                    + "^(?=.*[0-9])"
                    + "(?=.*[a-z])"
                    + "(?=.*[A-Z])"
                    + "(?=.*[!@#&()\\[{}\\]:;',?*~$^\\-+=<>])"
                    + ".{8,50}$",
            message = "La Password non rispetta il formato corretto!"
    )
    private String password;

    /**
     * Email dell' Utente.<br>
     * Deve essere unica e rispettare il pattern.
     */
    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$",
            message = "L'Email non rispetta il formato corretto!")
    private String email;

    /**
     * Numero di telefono dell' Utente.<br>
     * Può essere Null e deve rispettare il pattern.
     */
    @Size(max = 15)
    @Pattern(regexp = "^$|"
            + "^([+]?[(]?[0-9]{1,3}[)]?[-\\s])?"
            + "([(]?[0-9]{3}[)]?[-\\s]?)?"
            + "([0-9][-\\s]?){3,10}[0-9]$",
            message = "Il Numero di telefono non rispetta il formato corretto!")
    private String numeroTel;

    /**
     * Data di nascita dell' Utente.<br>
     */
    @NotNull
    @Past
    private LocalDate dataNascita;

    /**
     * Nome dell' Utente.<br>
     * Deve rispettare il pattern.
     */
    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp = "^(?=.{2,50}$)[A-Za-zÀ-ÿ]+([-,. '][A-Za-zÀ-ÿ]+)*$",
            message = "Il Nome non rispetta il formato corretto!")
    private String nome;

    /**
     * Nome dell' Utente.<br>
     * Deve rispettare il pattern.
     */
    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp = "^(?=.{2,50}$)[A-Za-zÀ-ÿ]+([-,. '][A-Za-zÀ-ÿ]+)*$",
            message = "Il Cognome non rispetta il formato corretto!")
    private String cognome;
}
