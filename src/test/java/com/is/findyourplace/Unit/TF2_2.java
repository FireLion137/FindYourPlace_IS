package com.is.findyourplace.Unit;

import com.is.findyourplace.FindYourPlaceApplication;
import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.Notifica;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.NotificaRepository;
import com.is.findyourplace.persistence.repository.UtenteRepository;
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

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FindYourPlaceApplication.class )
@AutoConfigureMockMvc()
public class TF2_2 {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private UtenteRepository utenteRepository;
        @MockBean
        private NotificaRepository notificaRepository;

        Utente utente;
        List<Utente> listUtenti;
        @BeforeEach
        public void setUp() {
            utente = new Utente();
            utente.setIdUtente(1L);
            listUtenti = new ArrayList<>();
            listUtenti.add(utente);
        }
        @Test
        public void testSendNotB() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setAutore("testautore");
            notificaDto.setTesto("testtesto");

            Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);
            Mockito.when(utenteRepository.findAll()).thenReturn(listUtenti);
            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotificationAll")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(201));
        }
        @Test
        public void testSendNotBAutNull() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setTesto("testtesto");

            Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);
            Mockito.when(utenteRepository.findAll()).thenReturn(listUtenti);
            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotificationAll")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        }
        @Test
        public void testSendNotBTextNull() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setAutore("testautore");

            Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);
            Mockito.when(utenteRepository.findAll()).thenReturn(listUtenti);
            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotificationAll")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        }
}
