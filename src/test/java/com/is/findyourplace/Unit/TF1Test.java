package com.is.findyourplace.Unit;
import org.junit.jupiter.api.Test;
import com.is.findyourplace.FindYourPlaceApplication;
import com.is.findyourplace.persistence.dto.UtenteDto;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TF1Test {
    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistration() throws Exception {
        UtenteDto utenteDto = new UtenteDto();
        utenteDto.setNome("testnome");
        utenteDto.setCognome("testcognome");
        utenteDto.setDataNascita(LocalDate.of(1998, 3,2));
        utenteDto.setUsername("testuser");
        utenteDto.setEmail("test@example.com");
        utenteDto.setPassword("Testpassword1!");

        Mockito.when(accountService.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(accountService.existsByEmail("test@example.com")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("utenteR", utenteDto))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
}