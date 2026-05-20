package com.foodly.foodly.service;

import com.foodly.foodly.model.Categoria;
import com.foodly.foodly.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria salvar(Categoria categoria) {
        if (categoria.getTipo() == null || categoria.getTipo().isBlank()) {
            categoria.setTipo("PRODUTO");
        }
        return repository.findByNome(categoria.getNome())
                .orElseGet(() -> repository.save(categoria));
    }

    public Categoria buscarOuCriar(String nome, String tipo) {
        return repository.findByNome(nome).orElseGet(() -> {
            Categoria nova = new Categoria();
            nova.setNome(nome);
            nova.setTipo(tipo != null ? tipo : "PRODUTO");
            return repository.save(nova);
        });
    }

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public List<Categoria> listarPorTipo(String tipo) {
        return repository.findByTipo(tipo.toUpperCase());
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
