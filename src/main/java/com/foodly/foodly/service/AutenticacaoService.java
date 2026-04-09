package com.foodly.foodly.service;

import com.foodly.foodly.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Tentando carregar usuario: " + email);
        UserDetails usuario = repository.findByEmail(email);

        if (usuario == null) {
            System.out.println("Usuario NAO encontrado no banco!");
            throw new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email);
        }

        System.out.println("Usuario encontrado! Senha no banco: " + usuario.getPassword());
        return usuario;
    }
}