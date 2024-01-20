package com.is.findyourplace.persistence.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


/**
 * Classe Dto di Notifica
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificaDto {

    /**
     * Id dell'utente
     */
    private Long idNotifica;

    /**
     * Autore della notifica
     */
    @NotEmpty
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$")
    private String autore;

     /**
        * testo della notifica
     */

    @NotEmpty
    @Size(max = 501)
    @Pattern(regexp = "^[\\s\\S]{2,500}$")
    private String testo;

    /**
     * Data di invio della notifica.<br>
     */
    @NotNull
    @PastOrPresent
    private LocalDateTime dataInvio;

    /**
     * Data di scadenza della notifica.<br>
     */
    @NotNull
    @Future
    private LocalDateTime dataScadenza;

    /**
     * Destinatario della notifica
     */
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$")
    private String destinatario;




}
