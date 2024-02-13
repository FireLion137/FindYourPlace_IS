package com.is.findyourplace.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

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
     * Id del Luogo.
     */
    @Id
    @Column(name = "id_luogo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLuogo = 0L;

    /**
     * Nome del Luogo.
     */
    @Size(min = 2, max = 100)
    @Column(unique = true)
    @NotNull
    private String nome;

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
    @DecimalMin("2")
    @DecimalMax("100")
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
        return "Luogo{"
                + "idLuogo=" + idLuogo
                + ", coordinate=" + coordinate
                + ", qualityIndex=" + qualityIndex
                + ", lastFoundDate=" + lastFoundDate
                + '}';
    }
}
