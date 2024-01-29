package com.is.findyourplace.persistence.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

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
     * Coordinate su cui verrà eseguita la Ricerca.
     */
    @NotNull
    private Point coordinate;

    /**
     * Raggio a partire dal punto su cui si effettua la Ricerca.
     */
    @NotNull
    @PositiveOrZero
    @Size(min = 2, max = 500)
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
    @Size(min = 2, max = 100)
    private float dangerMax;

    /**
     * Campo che definisce il numero di abitanti minimo.
     */
    @PositiveOrZero
    @Size(max = 1000000)
    private int numAbitantiMin;

    /**
     * Campo che definisce il numero di abitanti massimo.
     */
    @PositiveOrZero
    @Size(min = 1000, max = 10000000)
    private int numAbitantiMax;

    /**
     * Campo che definisce il numero di negozi minimo.
     */
    @PositiveOrZero
    @Size(max = 1000)
    private int numNegoziMin;

    /**
     * Campo che definisce il numero di scuole minimo.
     */
    @PositiveOrZero
    @Size(max = 100)
    private int numScuoleMin;

    /**
     * Campo che definisce il numero di ristoranti minimo.
     */
    @PositiveOrZero
    @Size(max = 10000)
    private int numRistorantiMin;
}
