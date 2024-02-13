package com.is.findyourplace.Unit;

import com.is.findyourplace.FindYourPlaceApplication;
import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.*;
import com.is.findyourplace.persistence.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FindYourPlaceApplication.class )
@AutoConfigureMockMvc()
public class TF3_1 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RicercaRepository ricercaRepository;

    @MockBean
    private UtenteRepository utenteRepository;

    @MockBean
    private LuogoRepository luogoRepository;

    @MockBean
    private LuogoTrovatoRepository luogoTrovatoRepository;

    @MockBean
    private NotificaRepository notificaRepository;

    @MockBean
    private PreferitiRepository preferitiRepository;

    RicercaDto ricercaDto;
    Utente utente;
    Ricerca ricerca;
    @BeforeEach
    public void setUp() {
        ricercaDto = new RicercaDto();
        ricercaDto.setLatitude(40.8517746F);
        ricercaDto.setLongitude(14.2681244F);
        ricercaDto.setRaggio(25);
        ricercaDto.setCostoVita(Filtri.CostoVita.QUALSIASI);
        ricercaDto.setDangerMax(100);
        ricercaDto.setNumAbitantiMax(1000000);
        ricercaDto.setNumNegoziMin(0);
        ricercaDto.setNumRistorantiMin(0);
        ricercaDto.setNumScuoleMin(0);

        utente = new Utente();
        utente.setIdUtente(1L);

        ricerca = new Ricerca();
        ricerca.setIdRicerca(1L);
        ricerca.setUtente(utente);
    }

    @Test
    public void testSearch() throws Exception {
        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }
    @Test
    public void testSearchAbitantiError() throws Exception {
        ricercaDto.setNumAbitantiMax(15000);
        ricercaDto.setNumNegoziMin(20000);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testSearchRaggioError() throws Exception {
        ricercaDto.setRaggio(0);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testSearchCoordinateError() throws Exception {
        ricercaDto.setLatitude(0);
        ricercaDto.setLongitude(0);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testSearchDangerError() throws Exception {
        ricercaDto.setDangerMax(0);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testSearchnumAbitantiMinError() throws Exception {
        ricercaDto.setNumAbitantiMin(999999999);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testSearchnumAbitantiMaxError() throws Exception {
        ricercaDto.setNumAbitantiMax(0);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testSearchnumNegoziMinError() throws Exception {
        ricercaDto.setNumNegoziMin(999999999);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testSearchnumRistorantiMinError() throws Exception {
        ricercaDto.setNumRistorantiMin(999999999);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testSearchnumScuoleMinError() throws Exception {
        ricercaDto.setNumScuoleMin(999999999);

        Mockito.when(ricercaRepository.save(Mockito.any(Ricerca.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);

        Mockito.when(luogoRepository.existsByNome(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(luogoRepository.save(Mockito.any(Luogo.class))).thenReturn(null);

        Mockito.when(ricercaRepository.findByIdRicerca(null)).thenReturn(ricerca);

        Mockito.when(notificaRepository.existsByAutoreAndTestoAndExpireDateAfter(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)
                )
        ).thenReturn(false);

        Mockito.when(utenteRepository.findUtentiByIdLuogoPreferito(
                Mockito.any(Long.class),
                Mockito.anyInt()
        )).thenReturn(List.of(utente));

        Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("ricerca", ricercaDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testViewResultPage() throws Exception {
        Luogo luogo = new Luogo();
        luogo.setIdLuogo(1L);
        luogo.setNome("Test");
        luogo.setCoordinate(
                new Point(
                        new CoordinateArraySequence(new Coordinate[]{
                                new Coordinate(
                                        40.9045572,
                                        14.2901223
                                )
                        }),
                        new GeometryFactory()
                )
        );

        LuogoTrovato luogoTrovato = new LuogoTrovato(ricerca, luogo);
        luogoTrovato.setQualityIndex(37.66F);
        luogoTrovato.setCostoVita(LuogoTrovato.CostoVita.BASSO);
        luogoTrovato.setDanger(39.54139399955827F);
        luogoTrovato.setNumAbitanti(74268);
        luogoTrovato.setNumNegozi(112);
        luogoTrovato.setNumRistoranti(96);
        luogoTrovato.setNumScuole(23);

        Preferiti preferito = new Preferiti(utente, luogo);
        Mockito.when(utenteRepository.findByUsernameOrEmail(Mockito.any(String.class), Mockito.any(String.class))).thenReturn(utente);
        Mockito.when(preferitiRepository.findByIdPreferiti(Mockito.any())).thenReturn(preferito);
        Mockito.when(luogoTrovatoRepository.findByIdRicerca(Mockito.any())).thenReturn(List.of(luogoTrovato));
        Mockito.when(luogoRepository.findByIdLuogo(Mockito.anyLong())).thenReturn(luogo);

        mockMvc.perform(MockMvcRequestBuilders.get("/searchResult?ricerca=1")
                        .with(SecurityMockMvcRequestPostProcessors.user("test")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}