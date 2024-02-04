package com.is.findyourplace.controller.gestioneRicerca;

import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Ricerca;
import com.is.findyourplace.service.gestioneRicerca.HistoryService;
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
 * Gestisce la cronologia delle ricerche
 */
@Controller
public class HistoryController {
    private final HistoryService historyService;
    private final AccountService accountService;
    public HistoryController(
            HistoryService historyService,
            AccountService accountService) {
        this.historyService = historyService;
        this.accountService = accountService;
    }

    @GetMapping("/searchHistory")
    public String searchHistory(final Model model) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/serverError";
        }
        List<RicercaDto> ricerche =
                historyService.findRicercheDtoByIdUtente(
                        accountService.findByUsernameOrEmail(auth.getName())
                                .getIdUtente()
                );

        model.addAttribute("ricerche", ricerche);
        return "ricerca/searchHistory";
    }

    @PostMapping("/searchHistory/deleteSearch")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteSearch(
            @RequestParam final Long idRicerca) {
        Map<String, Object> response = new HashMap<>();

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            Long idUtente = accountService.findByUsernameOrEmail(
                    auth.getName()).getIdUtente();
            Ricerca ricerca = historyService.findRicerca(idRicerca, idUtente);
            if(ricerca == null) {
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            historyService.removeIdUtente(ricerca);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
