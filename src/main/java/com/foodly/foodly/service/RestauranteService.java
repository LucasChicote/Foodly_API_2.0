package com.foodly.foodly.service;

import com.foodly.foodly.dto.RestauranteDTO;
import com.foodly.foodly.model.Restaurante;
import com.foodly.foodly.model.Usuario;
import com.foodly.foodly.repository.RestauranteRepository;
import com.foodly.foodly.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public RestauranteService(RestauranteRepository restauranteRepository,
                              UsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RestauranteDTO.Response criar(RestauranteDTO.Request dto, String emailDono) {
        Usuario dono = usuarioRepository.findUsuarioByEmail(emailDono)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.nome());
        restaurante.setDescricao(dto.descricao());
        restaurante.setCategoria(dto.categoria());
        restaurante.setImagemUrl(dto.imagemUrl());
        restaurante.setDono(dono);

        return RestauranteDTO.Response.fromRestaurante(restauranteRepository.save(restaurante));
    }

    public List<RestauranteDTO.Response> listarTodos() {
        return restauranteRepository.findAll()
                .stream()
                .map(RestauranteDTO.Response::fromRestaurante)
                .toList();
    }

    public List<RestauranteDTO.Response> listarDoOwner(String emailDono) {
        Usuario dono = usuarioRepository.findUsuarioByEmail(emailDono)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return restauranteRepository.findByDonoId(dono.getId())
                .stream()
                .map(RestauranteDTO.Response::fromRestaurante)
                .toList();
    }
}