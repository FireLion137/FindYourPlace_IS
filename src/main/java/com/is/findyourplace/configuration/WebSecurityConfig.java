package com.is.findyourplace.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    /**
     * Autowiring UserDetailsService because
     * there is a CustomUserDetailsService.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Define the type of PasswordEncoder used for Authentication.
     * @return BCryptPasswordEncoder
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define the security filter for every http.
     * @param http link http passed
     * @return Builded security http
     * @throws Exception in case there is an error
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http)
            throws Exception {
        http
            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                    .requestMatchers("/", "/index").permitAll()
                    .requestMatchers("/accountAuth", "/register").permitAll()
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/editProfile",
                            "/editPreferences").authenticated()
                    .requestMatchers("/search", "/searchResult").permitAll()
                    .requestMatchers("/searchHistory",
                            "/searchHistory/deleteSearch",
                            "/savedPlaces",
                            "/savedPlaces/deletePlace").authenticated()
                    .requestMatchers("/retrieveNot").permitAll()
                    .anyRequest().permitAll()
            )
            .formLogin((form) -> form
                    .loginPage("/accountAuth")
                    .loginProcessingUrl("/login")
                    .permitAll()
            )
            .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll()
            );

        return http.build();
    }

    /**
     * Autowiring to encode with specified passwordEncoder.
     * @param auth AuthenticationManagerBuilder
     * @throws Exception in case of errors
     */
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
