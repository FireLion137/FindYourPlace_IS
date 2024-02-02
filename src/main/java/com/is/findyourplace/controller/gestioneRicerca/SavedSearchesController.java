package com.is.findyourplace.controller.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoPreferitoDto;
import com.is.findyourplace.persistence.entity.Preferiti;
import com.is.findyourplace.service.gestioneRicerca.SavedPlacesService;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestisce il salvataggio e visualizzazione dei luoghi preferiti
 */
@Controller
public class SavedSearchesController {
    private final SavedPlacesService savedPlacesService;
    private final AccountService accountService;

    public SavedSearchesController(
            SavedPlacesService savedPlacesService,
            AccountService accountService) {
        this.savedPlacesService = savedPlacesService;
        this.accountService = accountService;
    }

    @GetMapping("/savedPlaces")
    public String searchHistory(final Model model) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/serverError";
        }
        List<LuogoPreferitoDto> luoghiPreferitiDto =
                savedPlacesService.findLuoghiPreferitiDtoByIdUtente(
                        accountService.findByUsername(auth.getName())
                                .getIdUtente()
                );

        model.addAttribute("luoghiPreferiti", luoghiPreferitiDto);
        return "ricerca/savedPlaces";
    }

    @PostMapping("/savedPlaces/deletePlace")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteSearch(
            @RequestParam final Long idLuogo) {
        Map<String, Object> response = new HashMap<>();

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            Long idUtente = accountService.findByUsernameOrEmail(
                    auth.getName()).getIdUtente();
            Preferiti preferito = savedPlacesService.findPreferito(idUtente, idLuogo);
            if(preferito == null) {
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            savedPlacesService.deletePreferito(preferito);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
