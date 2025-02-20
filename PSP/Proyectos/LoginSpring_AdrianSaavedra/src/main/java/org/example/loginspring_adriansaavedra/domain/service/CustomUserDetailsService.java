package org.example.loginspring_adriansaavedra.domain.service;

import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.dao.DaoCredenciales;
import org.example.loginspring_adriansaavedra.domain.model.CredentialEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DaoCredenciales daoCredenciales;

    public CustomUserDetailsService(DaoCredenciales daoCredenciales) {
        this.daoCredenciales = daoCredenciales;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CredentialEntity credentialEntity = daoCredenciales.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constantes.USER_NOT_FOUND + username));

        return new User(credentialEntity.getUsername(),
                credentialEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(Constantes.ROLE_BAR + credentialEntity.getRole().getRol())));
    }
}

