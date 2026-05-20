package com.foodly.foodly.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequestDTO(

        @NotBlank(message = "Nome do produto é obrigatório")
        String nome,

        String descricao,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        Double preco,

        String imagemUrl,

        Long categoriaId,

        String categoriaNome,

        @NotNull(message = "Restaurante é obrigatório")
        Long restauranteId
) {}
