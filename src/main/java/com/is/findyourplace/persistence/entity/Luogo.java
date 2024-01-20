package com.is.findyourplace.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe relativa a un Luogo.<br>
 * I campi sono:
 *  id luogo,
 *  coordinate,
 *  Indice di Qualità di vita (Aggiornato sempre),
 *  Data dell'ultima volta che è stato trovato il luogo.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Luogo {
    /**
     * Id del Luogo
     */
    @Id
    @NotNull
    @Column(name = "id_luogo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLuogo;

    /**
     * Coordinate del Luogo.
     */
    @NotNull
    private Point coordinate;

    /**
     * Indice di Qualità in percentuale aggiornato
     * ogni qual volta il luogo viene trovato in una ricerca.
     */
    @NotNull
    @Size(min = 2, max = 100)
    private float qualityIndex;

    /**
     * Data e ora dell' ultima volta che è stato trovato il Luogo.
     */
    @NotNull
    @UpdateTimestamp
    private LocalDateTime lastFoundDate;

    /**
     * Lista delle ricerche che hanno trovato il luogo.
     */
    @OneToMany(mappedBy = "luogo", cascade = CascadeType.ALL)
    List<LuogoTrovato> luoghiTrovati = new ArrayList<>();

    /**
     * Lista degli utenti che hanno salvato il luogo nei preferiti.
     */
    @OneToMany(mappedBy = "luogo", cascade = CascadeType.ALL)
    List<Preferiti> preferiti = new ArrayList<>();

    @Override
    public String toString() {
        return "Luogo{" +
                "idLuogo=" + idLuogo +
                ", coordinate=" + coordinate +
                ", qualityIndex=" + qualityIndex +
                ", lastFoundDate=" + lastFoundDate +
                '}';
    }
}
