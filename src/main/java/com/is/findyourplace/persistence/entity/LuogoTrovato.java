package com.is.findyourplace.persistence.entity;

import com.is.findyourplace.persistence.entity.CompositeKeys.LuogoTrovatoKey;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MapsId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe relativa a un Luogo Trovato in una ricerca.<br>
 * I campi sono:
 *  id ricerca,
 *  id luogo,
 *  Indice di Qualità di vita (Al momento della ricerca),
 *  costo della vita (ALTO - MEDIO - BASSO),
 *  danger in percentuale,
 *  numAbitanti,
 *  numNegozi,
 *  NumScuole,
 *  NumRistoranti.
 *  @author Pietro Esposito
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuogoTrovato {
    /**
     * Id composto del Luogo Trovato.
     */
    @EmbeddedId
    private LuogoTrovatoKey idLuogoTrovato;

    /**
     * Ogni LuogoTrovato è collegato a una Ricerca.
     */
    @ManyToOne
    @MapsId("idRicerca")
    @JoinColumn(name = "id_ricerca")
    Ricerca ricerca;

    /**
     * Ogni LuogoTrovato è collegato a un Luogo.
     */
    @ManyToOne
    @MapsId("idLuogo")
    @JoinColumn(name = "id_luogo")
    Luogo luogo;

    /**
     * Indice di Qualità in percentuale trovato al momento della ricerca.
     */
    @NotNull
    @DecimalMin("2")
    @DecimalMax("100")
    private float qualityIndex;

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
     * Campo che definisce il costo della vita.
     */
    @Enumerated(EnumType.STRING)
    private CostoVita costoVita;

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

    /**
     * Costruttore LuogoTrovato.
     * @param ricerca Ricerca che ha trovato il Luogo
     * @param luogo Luogo trovato
     */
    public LuogoTrovato(final Ricerca ricerca, final Luogo luogo) {
        this.ricerca = ricerca;
        this.luogo = luogo;
        this.idLuogoTrovato = new LuogoTrovatoKey(
                ricerca.getIdRicerca(),
                luogo.getIdLuogo()
        );
    }
}
