package com.foodly.foodly.dto;

import com.foodly.foodly.model.Produto;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        String imagemUrl,
        String categoria,
        Long categoriaId,
        String restaurante,
        Long restauranteId
) {
    public static ProdutoResponseDTO fromProduto(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagemUrl(),
                produto.getCategoria() != null ? produto.getCategoria().getNome() : null,
                produto.getCategoria() != null ? produto.getCategoria().getId() : null,
                produto.getRestaurante() != null ? produto.getRestaurante().getNome() : null,
                produto.getRestaurante() != null ? produto.getRestaurante().getId() : null
        );
    }
}