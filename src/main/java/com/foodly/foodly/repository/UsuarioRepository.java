package com.foodly.foodly.repository;

import com.foodly.foodly.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String email);

    Optional<Usuario> findUsuarioByEmail(String email);

    boolean existsByEmail(String email);
}