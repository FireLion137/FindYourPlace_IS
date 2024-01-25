package com.is.findyourplace.persistence.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


/**
 * Classe Dto di Notifica.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificaDto {

    /**
     * Id dell'utente.
     */
    private Long idNotifica;

    /**
     * Autore della notifica.
     */
    @NotEmpty
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$")
    private String autore;

     /**
        * Testo della notifica.
     */

    @NotEmpty
    @Size(max = 501)
    @Pattern(regexp = "^[\\s\\S]{2,500}$")
    private String testo;

    /**
     * Data di invio della notifica.
     */
    @PastOrPresent
    private LocalDateTime dataInvio;

    /**
     * Data di scadenza della notifica.
     */
    @Future
    private LocalDateTime dataScadenza;

    /**
     * Destinatario della notifica.<br>
     * Se la notifica è di tipo broadcast,
     * questo campo sarà null.
     */
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$")
    private String destinatario;
}
