package com.is.findyourplace.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.FetchType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

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
     * Id dell Ricerca.
     */
    @Id
    @Column(name = "id_ricerca")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRicerca;

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
    @Min(10)
    @Max(150)
    private int raggio;

    /**
     * Id dell' utente che ha effettuato la ricerca.
     */
    @Column(name = "id_utente")
    private Long idUtente;

    /**
     * Utente che ha effettuato la ricerca.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUtente")
    @JoinColumn(name = "id_utente")
    Utente utente;


    /**
     * Filtri della ricerca.
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
        return "Ricerca{"
                + "idRicerca=" + idRicerca
                + ", dataRicerca=" + dataRicerca
                + ", coordinate=" + coordinate
                + ", raggio=" + raggio
                + ", idUtente=" + idUtente
                + ", filtri=" + filtri
                + '}';
    }
}
