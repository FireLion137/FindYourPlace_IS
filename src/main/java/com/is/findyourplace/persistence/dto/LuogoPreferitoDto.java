package com.is.findyourplace.persistence.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe Dto di un LuogoPreferito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuogoPreferitoDto {
    /**
     * Id dell' Utente.
     */
    private Long idUtente;

    /**
     * Id del Luogo.
     */
    private Long idLuogo;

    /**
     * Nome del Luogo.
     */
    @Size(min = 2, max = 100)
    @NotNull
    private String nome;

    /**
     * Latitudine del Luogo.
     */
    @NotNull
    @DecimalMin(value = "36.619987291")
    @DecimalMax(value = "47.1153931748")
    private float latitude;

    /**
     * Longitudine del Luogo.
     */
    @NotNull
    @DecimalMin(value = "6.7499552751")
    @DecimalMax(value = "18.4802470232")
    private float longitude;

    /**
     * Indice di Qualità in percentuale aggiornato
     * ogni qual volta il luogo viene trovato in una ricerca.
     */
    @NotNull
    @DecimalMin("2")
    @DecimalMax("100")
    private float qualityIndexLuogo;

    /**
     * Indice di Qualità in percentuale
     * al momento del salvataggio nei preferiti.
     */
    @NotNull
    @Size(min = 2, max = 100)
    private float qualityIndexFound;

    /**
     * Campo che definisce se le notifiche sono attive.
     */
    @NotNull
    private boolean notifiche = true;
}
