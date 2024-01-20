package com.is.findyourplace.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe relativa a una Ricerca effettuata da un Utente.<br>
 * I campi sono:
 *  id ricerca,
 *  data della ricerca,
 *  coordinate,
 *  raggio,
 *  id utente che ha effettuato la ricerca.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ricerca {
    /**
     * Id dell Ricerca
     */
    @Id
    @NotNull
    @Column(name = "id_ricerca")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRicerca;

    /**
     * Data e ora della Ricerca.
     */
    @NotNull
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
     * Id dell' utente che ha effettuato la ricerca.
     */
    @NotNull
    @Column(name = "id_utente")
    private long idUtente;

    /**
     * Utente che ha effettuato la ricerca.
     */
    @ManyToOne
    @MapsId("idUtente")
    @JoinColumn(name = "id_utente")
    Utente utente;


    /**
     * Filtri della ricerca
     */
    @OneToOne(mappedBy = "ricerca", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Filtri filtri;

    /**
     * Lista dei luoghi trovati in una ricerca.
     */
    @OneToMany(mappedBy = "ricerca", cascade = CascadeType.ALL)
    List<LuogoTrovato> luoghiTrovati = new ArrayList<>();

    @Override
    public String toString() {
        return "Ricerca{" +
                "idRicerca=" + idRicerca +
                ", dataRicerca=" + dataRicerca +
                ", coordinate=" + coordinate +
                ", raggio=" + raggio +
                ", idUtente=" + idUtente +
                ", utente=" + utente +
                ", filtri=" + filtri +
                '}';
    }
}
