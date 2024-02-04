package com.is.findyourplace.controller.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoPreferitoDto;
import com.is.findyourplace.persistence.entity.Preferiti;
import com.is.findyourplace.persistence.entity.Utente;
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
public class SavedPlacesController {
    private final SavedPlacesService savedPlacesService;
    private final AccountService accountService;

    public SavedPlacesController(
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
                        accountService.findByUsernameOrEmail(auth.getName())
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

    @PostMapping("/savedPlaces/setNot")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> setNot(
            @RequestParam final Long idLuogo,
            @RequestParam final boolean isActive) {
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
            savedPlacesService.updateNotPreferito(preferito, !isActive);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/savedPlaces/setPref")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> setPref(
            @RequestParam final Long idLuogo,
            @RequestParam final boolean isPref) {
        Map<String, Object> response = new HashMap<>();

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            Utente utente =
                    accountService.findByUsernameOrEmail(auth.getName());
            Preferiti preferito =
                    savedPlacesService.findPreferito(
                            utente.getIdUtente(),
                            idLuogo
                    );
            if ((preferito == null && isPref) || (preferito != null && !isPref)) {
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            if (isPref) {
                savedPlacesService.deletePreferito(preferito);
            } else {
                savedPlacesService.savePreferito(utente, savedPlacesService.findLuogoById(idLuogo));
            }
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
