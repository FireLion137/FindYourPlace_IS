package com.is.findyourplace.controller.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoDto;
import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.service.gestioneRicerca.SearchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> registration(
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

        searchService.saveRicerca(ricercaDto);

        //Call al modulo di IA
        //response.put()

        LuogoDto luogoDto = new LuogoDto();
        //Temporaneo, va cambiato con i dati ricevuti dal modulo


        searchService.saveLuogoDto(luogoDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
