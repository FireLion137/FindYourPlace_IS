package com.is.findyourplace.Unit;

import com.is.findyourplace.FindYourPlaceApplication;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.persistence.entity.Preferenze;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.PreferenzeRepository;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FindYourPlaceApplication.class )
@AutoConfigureMockMvc()
public class TF1_3 {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtenteRepository utenteRepository;

    @MockBean
    private PreferenzeRepository preferenzeRepository;

    UtenteDto utenteDto;
    Utente utente;
    @BeforeEach
    public void setUp() {
        utenteDto = new UtenteDto();
        utenteDto.setNome("testnome");
        utenteDto.setCognome("testcognome");
        utenteDto.setDataNascita(LocalDate.of(1998, 3,2));
        utenteDto.setUsername("testuser");
        utenteDto.setEmail("test@example.com");
        utenteDto.setPassword("PasswordTest123!");

        utente = new Utente();
        utente.setIdUtente(1L);
        utente.setUsername(utenteDto.getUsername());
        utente.setEmail(utenteDto.getEmail());
    }

    @Test
    public void testViewEditProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/editProfile"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);
        mockMvc.perform(MockMvcRequestBuilders.get("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors.user("test")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    public void testViewEditPreferences() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/editPreferences"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Mockito.when(utenteRepository.findByUsernameOrEmail(
                Mockito.any(String.class), Mockito.any(String.class)
        )).thenReturn(utente);
        Mockito.when(preferenzeRepository.findByIdUtente(utente.getIdUtente())).thenReturn(null);
        Mockito.when(preferenzeRepository.save(Mockito.any(Preferenze.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/editPreferences")
                        .with(SecurityMockMvcRequestPostProcessors.user("test")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testEditProfile() throws Exception {
        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        Mockito.when(utenteRepository.save(Mockito.any(Utente.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utente", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testEditProfileWithoutPass() throws Exception {
        utenteDto.setPassword("");

        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        Mockito.when(utenteRepository.save(Mockito.any(Utente.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utente", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testEditProfileNullName() throws Exception {
        utenteDto.setNome(null);

        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        Mockito.when(utenteRepository.save(Mockito.any(Utente.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utente", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testEditProfileNullSurname() throws Exception {
        utenteDto.setCognome(null);

        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        Mockito.when(utenteRepository.save(Mockito.any(Utente.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utente", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testEditProfileNullDate() throws Exception {
        utenteDto.setDataNascita(null);

        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        Mockito.when(utenteRepository.save(Mockito.any(Utente.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utente", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testEditProfileNotNullNumTel() throws Exception {
        utenteDto.setNumeroTel("3337774444");

        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        Mockito.when(utenteRepository.save(Mockito.any(Utente.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utente", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void testEditProfileNullUsername() throws Exception {
        utenteDto.setUsername(null);

        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        Mockito.when(utenteRepository.save(Mockito.any(Utente.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utente", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    public void testEditProfileNullEmail() throws Exception {
        utenteDto.setEmail(null);

        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        Mockito.when(utenteRepository.save(Mockito.any(Utente.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/editProfile")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utente", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testEditPreferences() throws Exception {
        Preferenze preferenze = new Preferenze();
        preferenze.setIdUtente(utente.getIdUtente());
        preferenze.setUtente(utente);

        preferenze.setNotifiche(false);
        preferenze.setStudente(true);
        preferenze.setGenitore(true);

        Mockito.when(accountService.findByUsernameOrEmail("testuser")).thenReturn(utente);

        Mockito.when(preferenzeRepository.save(Mockito.any(Preferenze.class))).thenReturn(null);
        Mockito.when(utenteRepository.findByIdUtente(Mockito.any(Long.class))).thenReturn(utente);

        mockMvc.perform(MockMvcRequestBuilders.post("/editPreferences")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("testuser").password("PasswordTest123!"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("preferenze", preferenze))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}