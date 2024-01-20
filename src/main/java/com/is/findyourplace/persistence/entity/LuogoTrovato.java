package com.is.findyourplace.persistence.entity;

import com.is.findyourplace.persistence.entity.CompositeKeys.LuogoTrovatoKey;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
     * Id composto del Luogo Trovato
     */
    @EmbeddedId
    private LuogoTrovatoKey idLuogoTrovato;

    @ManyToOne
    @MapsId("idRicerca")
    @JoinColumn(name = "id_ricerca")
    Ricerca ricerca;

    @ManyToOne
    @MapsId("idLuogo")
    @JoinColumn(name = "id_luogo")
    Luogo luogo;

    /**
     * Indice di Qualità in percentuale trovato al momento della ricerca.
     */
    @NotNull
    @Size(min = 2, max = 100)
    private float qualityIndex;

    /**
     * Enum usato per forzare solo 3 stringhe precise.
     */
    public enum CostoVita {
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
    @Size(min = 2, max = 100)
    private float danger;

    /**
     * Campo che definisce il numero di abitanti.
     */
    @PositiveOrZero
    @Size(max = 10000000)
    private int numAbitanti;

    /**
     * Campo che definisce il numero di negozi.
     */
    @PositiveOrZero
    @Size(max = 10000)
    private int numNegozi;

    /**
     * Campo che definisce il numero di scuole.
     */
    @PositiveOrZero
    @Size(max = 100)
    private int numScuole;

    /**
     * Campo che definisce il numero di ristoranti.
     */
    @PositiveOrZero
    @Size(max = 1000)
    private int numRistoranti;

    public LuogoTrovato(Ricerca ricerca, Luogo luogo) {
        this.ricerca = ricerca;
        this.luogo = luogo;
        this.idLuogoTrovato = new LuogoTrovatoKey(
                ricerca.getIdRicerca(),
                luogo.getIdLuogo()
        );
    }
}