package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoDto;
import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Ricerca;
import com.is.findyourplace.persistence.entity.Filtri;
import com.is.findyourplace.persistence.entity.Luogo;
import com.is.findyourplace.persistence.entity.LuogoTrovato;
import com.is.findyourplace.persistence.entity.Utente;

import com.is.findyourplace.persistence.repository.LuogoRepository;
import com.is.findyourplace.persistence.repository.LuogoTrovatoRepository;
import com.is.findyourplace.persistence.repository.RicercaRepository;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import com.is.findyourplace.persistence.repository.FiltriRepository;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private final RicercaRepository ricercaRepository;
    private final UtenteRepository utenteRepository;
    private final LuogoRepository luogoRepository;
    private final LuogoTrovatoRepository luogoTrovatoRepository;
    private final FiltriRepository filtriRepository;

    public SearchServiceImpl(
            RicercaRepository ricercaRepository,
            UtenteRepository utenteRepository,
            LuogoRepository luogoRepository,
            LuogoTrovatoRepository luogoTrovatoRepository,
            FiltriRepository filtriRepository) {
        this.ricercaRepository = ricercaRepository;
        this.utenteRepository = utenteRepository;
        this.luogoRepository = luogoRepository;
        this.luogoTrovatoRepository = luogoTrovatoRepository;
        this.filtriRepository = filtriRepository;
    }

    @Override
    @Transactional
    public Long saveRicerca(RicercaDto ricercaDto) {
        Ricerca ricerca = new Ricerca();
        ricerca.setCoordinate(
                new Point(
                        new CoordinateArraySequence(new Coordinate[]{
                                        new Coordinate(
                                                ricercaDto.getLatitude(),
                                                ricercaDto.getLongitude()
                                        )
                                }),
                        new GeometryFactory()
                )
        );
        ricerca.setRaggio(ricercaDto.getRaggio());
        ricerca.setDataRicerca(LocalDateTime.now());

        Filtri filtri = new Filtri();
        filtri.setIdRicerca(ricerca.getIdRicerca());
        filtri.setRicerca(ricerca);

        filtri.setCostoVita(ricercaDto.getCostoVita());
        filtri.setDangerMax(ricercaDto.getDangerMax());
        filtri.setNumAbitantiMin(ricercaDto.getNumAbitantiMin());
        filtri.setNumAbitantiMax(ricercaDto.getNumAbitantiMax());
        filtri.setNumNegoziMin(ricercaDto.getNumNegoziMin());
        filtri.setNumRistorantiMin(ricercaDto.getNumRistorantiMin());
        filtri.setNumScuoleMin(ricercaDto.getNumScuoleMin());
        ricerca.setFiltri(filtri);

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if(auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            Utente utente = utenteRepository.findByUsername(auth.getName());
            ricerca.setIdUtente(utente.getIdUtente());
            ricerca.setUtente(utente);
            utente.getRicerche().add(ricerca);
        }

        ricercaRepository.save(ricerca);
        return ricerca.getIdRicerca();
    }

    @Override
    @Transactional
    public void saveLuogoDto(LuogoDto luogoDto) {
        Luogo luogo;

        if(!luogoRepository.existsByNome(luogoDto.getNome())) {
            luogo = new Luogo();
            luogo.setNome(luogoDto.getNome());
            luogo.setCoordinate(
                    new Point(
                            new CoordinateArraySequence(new Coordinate[]{
                                    new Coordinate(
                                            luogoDto.getLatitude(),
                                            luogoDto.getLongitude()
                                    )
                            }),
                            new GeometryFactory()
                    )
            );
        } else {
            luogo = luogoRepository.findByNome(luogoDto.getNome());
        }
        luogo.setQualityIndex(luogoDto.getQualityIndex());
        luogo.setLastFoundDate(LocalDateTime.now());

        luogoRepository.save(luogo);

        Ricerca ricerca = ricercaRepository.findByIdRicerca(
                luogoDto.getIdRicerca()
        );
        LuogoTrovato luogoTrovato =
                new LuogoTrovato(ricerca, luogo);
        ricerca.getLuoghiTrovati().add(luogoTrovato);
        luogo.getLuoghiTrovati().add(luogoTrovato);

        luogoTrovato.setQualityIndex(luogoDto.getQualityIndex());
        luogoTrovato.setCostoVita(luogoDto.getCostoVita());
        luogoTrovato.setDanger(luogoDto.getDanger());
        luogoTrovato.setNumAbitanti(luogoDto.getNumAbitanti());
        luogoTrovato.setNumNegozi(luogoDto.getNumNegozi());
        luogoTrovato.setNumRistoranti(luogoDto.getNumRistoranti());
        luogoTrovato.setNumScuole(luogoDto.getNumScuole());
    }

    @Override
    public List<LuogoDto> findLuoghiByIdRicerca(Long idRicerca) {
        List<LuogoDto> luoghiDto = new ArrayList<>();
        for (LuogoTrovato luogoTrovato:
                luogoTrovatoRepository.findByIdRicerca(idRicerca)) {
            Luogo luogo = luogoRepository.findByIdLuogo(
                    luogoTrovato.getIdLuogoTrovato().getIdLuogo());

            luoghiDto.add(mapToLuogoDto(luogo, luogoTrovato));
        }

        return luoghiDto;
    }

    @Override
    public Filtri findFiltriByIdRicerca(Long idRicerca) {
        return filtriRepository.findByIdRicerca(idRicerca);
    }

    private LuogoDto mapToLuogoDto(
            final Luogo luogo,
            final LuogoTrovato luogoTrovato) {
        LuogoDto luogoDto = new LuogoDto();

        luogoDto.setNome(luogo.getNome());
        luogoDto.setLatitude((float) luogo.getCoordinate().getX());
        luogoDto.setLongitude((float) luogo.getCoordinate().getY());

        luogoDto.setQualityIndex(luogoTrovato.getQualityIndex());
        luogoDto.setCostoVita(luogoTrovato.getCostoVita());
        luogoDto.setDanger(luogoTrovato.getDanger());
        luogoDto.setNumAbitanti(luogoTrovato.getNumAbitanti());
        luogoDto.setNumNegozi(luogoTrovato.getNumNegozi());
        luogoDto.setNumRistoranti(luogoTrovato.getNumRistoranti());
        luogoDto.setNumScuole(luogoTrovato.getNumScuole());

        return luogoDto;
    }
}
