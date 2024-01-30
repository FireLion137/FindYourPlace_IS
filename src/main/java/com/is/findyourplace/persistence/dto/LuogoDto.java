package com.is.findyourplace.persistence.dto;

import com.is.findyourplace.persistence.entity.LuogoTrovato;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe Dto di un Luogo con tutte le informazioni trovate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuogoDto {
    /**
     * Id del Luogo.
     */
    private Long idLuogo;

    /**
     * Id della Ricerca.
     */
    private Long idRicerca;

    /**
     * Nome del Luogo
     */
    @Size(min = 2, max = 100)
    @NotNull
    private String nome;

    /**
     * Latitudine del Luogo.
     */
    @NotNull
    @DecimalMin(value= "36.619987291")
    @DecimalMax(value = "47.1153931748")
    private float latitude;

    /**
     * Longitudine del Luogo.
     */
    @NotNull
    @DecimalMin(value= "6.7499552751")
    @DecimalMax(value = "18.4802470232")
    private float longitude;

    /**
     * Indice di Qualità in percentuale aggiornato
     * ogni qual volta il luogo viene trovato in una ricerca.
     */
    @NotNull
    @DecimalMin("2")
    @DecimalMax("100")
    private float qualityIndex;

    /**
     * Campo che definisce il costo della vita.
     */
    private LuogoTrovato.CostoVita costoVita;

    /**
     * Campo che definisce la pericolosità.
     */
    @DecimalMin("2")
    @DecimalMax("100")
    private float danger;

    /**
     * Campo che definisce il numero di abitanti.
     */
    @PositiveOrZero
    @Max(10000000)
    private int numAbitanti;

    /**
     * Campo che definisce il numero di negozi.
     */
    @PositiveOrZero
    @Max(1000)
    private int numNegozi;

    /**
     * Campo che definisce il numero di scuole.
     */
    @PositiveOrZero
    @Max(100)
    private int numScuole;

    /**
     * Campo che definisce il numero di ristoranti.
     */
    @PositiveOrZero
    @Max(10000)
    private int numRistoranti;
}
