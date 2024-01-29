package com.is.findyourplace.persistence.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe Dto di Utente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RicercaDto {
    /**
     * Id della ricerca.
     */
    private Long idRicerca;

    /**
     * Data e ora della Ricerca.
     */
    private LocalDateTime dataRicerca;

    /**
     * Latitudine su cui verrà eseguita la Ricerca.
     */
    @NotNull
    @DecimalMin(value= "36.619987291")
    @DecimalMax(value = "47.1153931748")
    private float latitude;

    /**
     * Longitudine su cui verrà eseguita la Ricerca.
     */
    @NotNull
    @DecimalMin(value= "6.7499552751")
    @DecimalMax(value = "18.4802470232")
    private float longitude;

    /**
     * Raggio a partire dal punto su cui si effettua la Ricerca.
     */
    @NotNull
    @PositiveOrZero
    @Min(10)
    @Max(500)
    private int raggio;


    /**
     * Enum usato per forzare solo 3 stringhe precise.
     */
    public enum CostoVita {
        /**
         * Costo della vita Basso, Medio o Alto per regione.
         */
        BASSO, MEDIO, ALTO
    }
    /**
     * Campo che definisce il costo della vita della regione.
     */
    private RicercaDto.CostoVita costoVita;

    /**
     * Campo che definisce la pericolosità massima.
     */
    @DecimalMin(value = "25")
    @DecimalMax(value = "100")
    private float dangerMax;

    /**
     * Campo che definisce il numero di abitanti minimo.
     */
    @PositiveOrZero
    @Max(1000000)
    private int numAbitantiMin;

    /**
     * Campo che definisce il numero di abitanti massimo.
     */
    @PositiveOrZero
    @Min(1000)
    @Max(10000000)
    private int numAbitantiMax;

    /**
     * Campo che definisce il numero di negozi minimo.
     */
    @PositiveOrZero
    @Max(1000)
    private int numNegoziMin;

    /**
     * Campo che definisce il numero di scuole minimo.
     */
    @PositiveOrZero
    @Max(100)
    private int numScuoleMin;

    /**
     * Campo che definisce il numero di ristoranti minimo.
     */
    @PositiveOrZero
    @Max(10000)
    private int numRistorantiMin;
}
