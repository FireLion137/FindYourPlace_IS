package com.is.findyourplace.persistence.entity;

import com.is.findyourplace.persistence.entity.CompositeKeys.PreferitiKey;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe relativa ai luoghi Preferiti (salvati) di un utente.<br>
 * I campi sono:
 *  id utente,
 *  id luogo,
 *  Indice di Qualità di vita (Al momento del salvataggio nei preferiti),
 *  notifiche (attive o no)
 *  @author Pietro Esposito
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Preferiti {
    /**
     * Id composto del luogo salvato nei Preferiti
     */
    @EmbeddedId
    private PreferitiKey idPreferiti;

    @ManyToOne
    @MapsId("idUtente")
    @JoinColumn(name = "id_utente")
    Utente utente;

    @ManyToOne
    @MapsId("idLuogo")
    @JoinColumn(name = "id_luogo")
    Luogo luogo;

    /**
     * Indice di Qualità in percentuale al momento del salvataggio nei preferiti.
     */
    @NotNull
    @Size(min = 2, max = 100)
    private float qualityIndex;

    /**
     * Campo che definisce se le notifiche sono attive.
     */
    @NotNull
    private boolean notifiche = true;

    public Preferiti(Utente utente, Luogo luogo) {
        this.utente = utente;
        this.luogo = luogo;
        this.qualityIndex = luogo.getQualityIndex();
        this.idPreferiti = new PreferitiKey(
                utente.getIdUtente(),
                luogo.getIdLuogo()
        );
    }
}