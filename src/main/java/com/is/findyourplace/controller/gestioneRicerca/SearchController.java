package com.is.findyourplace.controller.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoDto;
import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Filtri;
import com.is.findyourplace.persistence.entity.LuogoTrovato;
import com.is.findyourplace.service.gestioneRicerca.SearchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestisce la Ricerca
 */
@Controller
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> search(
            @Valid @ModelAttribute("ricerca") final RicercaDto ricercaDto,
            final BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (ricercaDto.getNumAbitantiMin() >= ricercaDto.getNumAbitantiMax()) {
            result.rejectValue("numAbitantiMin", "null",
                    "Maggiore del numero abitanti massimo!");
            result.rejectValue("numAbitantiMax", "null",
                    "Minore del numero abitanti minimo!");
        }

        if (result.hasErrors()) {
            response.put("errors", result.getAllErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Long idRicerca= searchService.saveRicerca(ricercaDto);

        //Call al modulo di IA
        //response.put()

        LuogoDto luogoDto = new LuogoDto();
        // **** Temporaneo, va cambiato con i dati ricevuti dal modulo
        luogoDto.setIdRicerca(idRicerca);

        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(10);
        String alphanumeric= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 10; i++) {
            builder.append(alphanumeric.
                    charAt(random.nextInt(alphanumeric.length())));
        }
        luogoDto.setNome(builder.toString());

        luogoDto.setLatitude(ricercaDto.getLatitude());
        luogoDto.setLongitude(ricercaDto.getLongitude());
        luogoDto.setQualityIndex(50);
        luogoDto.setCostoVita(LuogoTrovato.CostoVita.MEDIO);
        luogoDto.setDanger(35);
        luogoDto.setNumAbitanti(10000);
        luogoDto.setNumNegozi(1000);
        luogoDto.setNumRistoranti(5000);
        luogoDto.setNumScuole(200);
        // **** Temporaneo, va cambiato con i dati ricevuti dal modulo

        searchService.saveLuogoDto(luogoDto);

        response.put("ricerca", idRicerca);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/searchResult")
    public String searchResult(
            @Valid @RequestParam final Long ricerca,
            final Model model) {

        List<LuogoDto> luoghi = searchService.findLuoghiByIdRicerca(ricerca);
        Filtri filtriUsati = searchService.findFiltriByIdRicerca(ricerca);
        model.addAttribute("luoghi", luoghi);
        model.addAttribute("filtri", filtriUsati);
        return "ricerca/ricercaResult";
    }
}
