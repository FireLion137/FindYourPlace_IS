package com.is.findyourplace.controller.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoDto;
import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.LuogoTrovato;
import com.is.findyourplace.persistence.entity.Preferiti;
import com.is.findyourplace.service.gestioneRicerca.SavedPlacesService;
import com.is.findyourplace.service.gestioneRicerca.SearchService;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestisce la Ricerca.
 */
@Controller
public class SearchController {
    /**
     * Template per le chiamate REST utilizzato
     * per comunicare con il server Flask.
     */
    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * Service usato per la ricerca.
     */
    private final SearchService searchService;
    /**
     *  Service dei luoghi preferiti.
     */
    private final SavedPlacesService savedPlacesService;
    /**
     *  Service per la gestione degli account.
     */
    private final AccountService accountService;

    /**
     * Costruttore del controller.
     *
     * @param searchService  searchService
     * @param savedPlacesService savedPlacesService
     * @param accountService accountService
     */
    public SearchController(final SearchService searchService,
                            final SavedPlacesService savedPlacesService,
                            final AccountService accountService) {
        this.searchService = searchService;
        this.savedPlacesService = savedPlacesService;
        this.accountService = accountService;
    }

    /**
     * Pagina che effettua la ricerca.
     * @param ricercaDto Parametri passati dal form
     * @param result Contiene gli errori
     * @return 201 CREATED / 400 BAD REQUEST
     */
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

        Long idRicerca = searchService.saveRicerca(ricercaDto);

        //Call al modulo di IA
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String flaskServerUrl = "http://127.0.0.1:5000/search-luoghi";

        // Effettua una chiamata REST al server Flask per avviare il modulo
        ResponseEntity<List<Map<String, Object>>> responseEntity =
                restTemplate.exchange(
                        flaskServerUrl,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<>() {
                        });
        List<Map<String, Object>> responseBody = responseEntity.getBody();

        if (responseBody == null) {
            return new ResponseEntity<>(response,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        for (Map<String, Object> luogo : responseBody) {
            LuogoDto luogoDto = new LuogoDto();
            luogoDto.setIdRicerca(idRicerca);

            luogoDto.setNome((String) luogo.get("nome"));

            luogoDto.setLatitude(
                    ((Double) luogo.get("latitude")).floatValue());
            luogoDto.setLongitude(
                    ((Double) luogo.get("longitude")).floatValue());
            luogoDto.setQualityIndex(
                    ((Double) luogo.get("qualityIndex")).floatValue());

            String costovita = (String) luogo.get("costoVita");
            if (costovita.equals("BASSO")) {
                luogoDto.setCostoVita(LuogoTrovato.CostoVita.BASSO);
            } else if (costovita.equals("MEDIO")) {
                luogoDto.setCostoVita(LuogoTrovato.CostoVita.MEDIO);
            } else {
                luogoDto.setCostoVita(LuogoTrovato.CostoVita.ALTO);
            }

            luogoDto.setDanger(((Double) luogo.get("danger")).floatValue());
            luogoDto.setNumAbitanti((Integer) luogo.get("numAbitanti"));
            luogoDto.setNumNegozi((Integer) luogo.get("numNegozi"));
            luogoDto.setNumRistoranti((Integer) luogo.get("numRistoranti"));
            luogoDto.setNumScuole((Integer) luogo.get("numScuole"));

            searchService.saveLuogoDto(luogoDto);
        }


        response.put("ricerca", idRicerca);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Pagina del risultato della ricerca effettuata.
     * @param ricerca Id della ricerca
     * @param model Model
     * @return ricerca/ricercaResult.html
     */
    @GetMapping("/searchResult")
    public String searchResult(
            @Valid @RequestParam final Long ricerca,
            final Model model) {
        Preferiti preferito;
        List<LuogoDto> luoghi = searchService.findLuoghiByIdRicerca(ricerca);
        if (luoghi.isEmpty()) {
            model.addAttribute("errorMessage",
                    "La ricerca non possiede risultati!");
            return "error";
        }

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            preferito = null;
        } else {
             preferito = savedPlacesService.findPreferito(
                     accountService.findByUsernameOrEmail(
                             auth.getName()
                     ).getIdUtente(),
                     luoghi.get(0).getIdLuogo()
             );
        }
        model.addAttribute("preferito", preferito);
        model.addAttribute("luoghi", luoghi);
        return "ricerca/ricercaResult";
    }
}
