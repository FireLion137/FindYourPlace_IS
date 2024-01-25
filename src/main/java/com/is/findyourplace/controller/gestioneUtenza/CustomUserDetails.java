package com.is.findyourplace.controller.gestioneUtenza;

import com.is.findyourplace.persistence.entity.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * Gestisce la creazione di un Custom User in spring boot security.
 */
public class CustomUserDetails extends User {
    /**
     * Costruttore CustomUserDetails.
     * @param username String - Username Utente
     * @param password String - Password Utente;
     * @param enabled boolean
     * @param accountNonExpired boolean
     * @param credentialsNonExpired boolean
     * @param accountNonLocked boolean
     * @param authorities Lista di ruoli personalizzati
     */
    public CustomUserDetails(
            final String username, final String password,
            final boolean enabled, final boolean accountNonExpired,
            final boolean credentialsNonExpired, final boolean accountNonLocked,
            final Collection<? extends GrantedAuthority> authorities) {
        super(
                username, password,
                enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked,
                authorities
        );
    }

    /**
     * Map Utente fields to UserDetails
     * Authorities based on the isAdmin flag
     * All users have the "USER" role,
     * Admins have both "USER" and "ADMIN" roles.
     * @param utente Utente
     * @return CustomUserDetails con ruoli personalizzati
     */
    public static CustomUserDetails fromUtente(final Utente utente) {
        return new CustomUserDetails(
                utente.getUsername(),
                utente.getPasswordHash(),
                true,
                true,
                true,
                true,
                utente.isAdmin()
                        ? List.of(() -> "ROLE_USER", () -> "ROLE_ADMIN")
                        : List.of(() -> "ROLE_USER")
        );
    }
}
