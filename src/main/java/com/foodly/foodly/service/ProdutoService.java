package com.foodly.foodly.service;

import com.foodly.foodly.dto.ProdutoRequestDTO;
import com.foodly.foodly.dto.ProdutoResponseDTO;
import com.foodly.foodly.model.Categoria;
import com.foodly.foodly.model.Produto;
import com.foodly.foodly.model.Restaurante;
import com.foodly.foodly.repository.CategoriaRepository;
import com.foodly.foodly.repository.ProdutoRepository;
import com.foodly.foodly.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final RestauranteRepository restauranteRepository;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository,
                          RestauranteRepository restauranteRepository,
                          CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.restauranteRepository = restauranteRepository;
        this.categoriaService = categoriaService;
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Categoria categoria;

        if (dto.categoriaId() != null) {
            categoria = categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + dto.categoriaId()));
        } else if (dto.categoriaNome() != null && !dto.categoriaNome().isBlank()) {
            categoria = categoriaService.buscarOuCriar(dto.categoriaNome(), "PRODUTO");
        } else {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }

        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + dto.restauranteId()));

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setImagemUrl(dto.imagemUrl());
        produto.setCategoria(categoria);
        produto.setRestaurante(restaurante);

        return ProdutoResponseDTO.fromProduto(produtoRepository.save(produto));
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::fromProduto)
                .toList();
    }

    public List<ProdutoResponseDTO> listarPorCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(ProdutoResponseDTO::fromProduto)
                .toList();
    }

    public List<ProdutoResponseDTO> listarKitsSustentaveis() {
        return produtoRepository.findByIsKitSustentavelTrueOrderByDataExpiracaoAsc()
                .stream()
                .map(ProdutoResponseDTO::fromProduto)
                .toList();
    }

    public List<ProdutoResponseDTO> buscarPorNomeOuDescricao(String termo) {
        return produtoRepository.findAll().stream()
                .filter(p -> p.getNome().toLowerCase().contains(termo.toLowerCase()) ||
                        (p.getDescricao() != null && p.getDescricao().toLowerCase().contains(termo.toLowerCase())))
                .map(ProdutoResponseDTO::fromProduto)
                .toList();
    }

    public List<ProdutoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(ProdutoResponseDTO::fromProduto)
                .toList();
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
