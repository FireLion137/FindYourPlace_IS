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
 * Gestisce la cronologia delle ricerche.
 */
@Controller
public class HistoryController {
    /**
     * Service per la cronologia delle ricerche.
     */
    private final HistoryService historyService;
    /**
     * Service per la gestione degli account.
     */
    private final AccountService accountService;
    /**
     * Construttore del controller.
     * @param accountService Service per la gestione degli account.
     * @param historyService Service per la cronologia delle ricerche.
     */
    public HistoryController(
            final HistoryService historyService,
            final AccountService accountService) {
        this.historyService = historyService;
        this.accountService = accountService;
    }
    /**
     * Mapping per la pagina della cronologia di ricerche.
     * @param model Model
     * @return ricerca/searchHistory.html
     */
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
    /**
     * Mapping per cancellare una ricerca dalla cronologia.
     * @param idRicerca id della ricerca
     * @return 200 OK / 401 UNAUTHORIZED
     */
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
            if (ricerca == null) {
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            historyService.removeIdUtente(ricerca);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
