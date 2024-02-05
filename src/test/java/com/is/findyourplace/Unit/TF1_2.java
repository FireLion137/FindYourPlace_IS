package com.is.findyourplace.Unit;

import com.is.findyourplace.FindYourPlaceApplication;
import com.is.findyourplace.controller.gestioneUtenza.CustomUserDetails;
import com.is.findyourplace.service.gestioneUtenza.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FindYourPlaceApplication.class )
@AutoConfigureMockMvc()
public class TF1_2 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testViewAuthPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountAuth"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get("/accountAuth")
                        .with(SecurityMockMvcRequestPostProcessors.user("test")))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void testLoginUsername() throws Exception {
        String username = "loginUserTest";
        String password = "loginPasswordTest123!";

        CustomUserDetails customUserDetails = new CustomUserDetails(
                username, passwordEncoder.encode(password), true, true,
                true, true, List.of(() -> "ROLE_USER")
        );

        Mockito.when(customUserDetailsService.loadUserByUsername(username))
                .thenReturn(customUserDetails);

        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                .user(username).password(password))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername(username));
    }

    @Test
    public void testLoginNullUsername() throws Exception {
        String username = "";
        String password = "loginPasswordTest123!";

        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                        .user(username).password(password))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/accountAuth?error"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    @Test
    public void testLoginNullPassword() throws Exception {
        String username = "loginUserTest";
        String password = "";

        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                        .user(username).password(password))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/accountAuth?error"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}