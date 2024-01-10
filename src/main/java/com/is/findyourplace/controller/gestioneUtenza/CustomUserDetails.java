package com.is.findyourplace.controller.gestioneUtenza;

import com.is.findyourplace.persistence.entity.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User
{
    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public static CustomUserDetails fromUtente(Utente utente) {
        // Map Utente fields to UserDetails
        // Authorities based on the isAdmin flag
        // All users have the "USER" role, and admins have both "USER" and "ADMIN" roles.

        return new CustomUserDetails(
                utente.getUsername(),
                utente.getPasswordHash(),
                true,
                true,
                true,
                true,
                utente.isAdmin() ? List.of(() -> "ROLE_USER", () -> "ROLE_ADMIN") : List.of(() -> "ROLE_USER")
        );
    }
}
