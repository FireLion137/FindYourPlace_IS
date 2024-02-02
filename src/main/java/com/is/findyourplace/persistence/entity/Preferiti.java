package com.is.findyourplace.persistence.entity;

import com.is.findyourplace.persistence.entity.CompositeKeys.PreferitiKey;
import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

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
     * Id composto del luogo salvato nei Preferiti.
     */
    @EmbeddedId
    private PreferitiKey idPreferiti;

    /**
     * Ogni luogo Preferito è collegata a un Utente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUtente")
    @JoinColumn(name = "id_utente")
    Utente utente;

    /**
     * Ogni luogo Preferito è collegato a un Luogo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idLuogo")
    @JoinColumn(name = "id_luogo")
    Luogo luogo;

    /**
     * Indice di Qualità in percentuale
     * al momento del salvataggio nei preferiti.
     */
    @NotNull
    @DecimalMin("2")
    @DecimalMax("100")
    private float qualityIndex;

    /**
     * Campo che definisce se le notifiche sono attive.
     */
    @NotNull
    private boolean notifiche = true;

    /**
     * Costruttore luogo Preferito.
     * @param utente Utente che ha salvato il luogo Preferito
     * @param luogo Luogo
     */
    public Preferiti(final Utente utente, final Luogo luogo) {
        this.utente = utente;
        this.luogo = luogo;
        this.qualityIndex = luogo.getQualityIndex();
        this.idPreferiti = new PreferitiKey(
                utente.getIdUtente(),
                luogo.getIdLuogo()
        );
    }
}
