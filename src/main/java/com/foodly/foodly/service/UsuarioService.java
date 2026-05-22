package com.foodly.foodly.service;

import com.foodly.foodly.client.ViaCepClient;
import com.foodly.foodly.dto.CadastroRequestDTO;
import com.foodly.foodly.dto.UsuarioResponseDTO;
import com.foodly.foodly.dto.BrasilApiCepDTO;
import com.foodly.foodly.model.Endereco;
import com.foodly.foodly.model.Usuario;
import com.foodly.foodly.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final ViaCepClient viaCepClient;
    private final PasswordEncoder passwordEncoder;
    private final RestClient restClient;

    public UsuarioService(UsuarioRepository repository,
                          ViaCepClient viaCepClient,
                          PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.viaCepClient = viaCepClient;
        this.passwordEncoder = passwordEncoder;
        this.restClient = RestClient.create("https://brasilapi.com.br/api");
    }

    public UsuarioResponseDTO cadastrar(CadastroRequestDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + dto.email());
        }

        Endereco endereco = buscarCep(dto.cep());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setCep(dto.cep());
        usuario.setRua(endereco.getLogradouro());
        usuario.setBairro(endereco.getBairro());
        usuario.setRole(dto.role());

        return UsuarioResponseDTO.fromUsuario(repository.save(usuario));
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(UsuarioResponseDTO::fromUsuario)
                .toList();
    }

    public Endereco buscarCep(String cep) {
        try {
            String cepLimpo = cep.replaceAll("\\D", "");
            BrasilApiCepDTO response = restClient.get()
                    .uri("/cep/v1/{cep}", cepLimpo)
                    .retrieve()
                    .body(BrasilApiCepDTO.class);

            Endereco endereco = new Endereco();
            if (response != null) {
                endereco.setCep(response.cep());
                endereco.setLogradouro(response.street());
                endereco.setBairro(response.neighborhood());
            }
            return endereco;
        } catch (Exception e) {
            try {
                return viaCepClient.buscarCep(cep);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Erro ao buscar o CEP informado em nenhum dos serviços");
            }
        }
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}