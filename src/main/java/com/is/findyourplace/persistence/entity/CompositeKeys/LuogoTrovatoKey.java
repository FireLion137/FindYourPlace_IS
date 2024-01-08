package com.is.findyourplace.persistence.entity.CompositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Chiave primaria composta di LuogoTrovato.
 * @author Pietro Esposito
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LuogoTrovatoKey implements Serializable {
    @NotNull
    @Column(name = "id_ricerca")
    private long idRicerca;

    @NotNull
    @Column(name = "id_luogo")
    private long idLuogo;
}
