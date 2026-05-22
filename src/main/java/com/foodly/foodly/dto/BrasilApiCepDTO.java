package com.foodly.foodly.dto;

public record BrasilApiCepDTO(
        String cep,
        String street,
        String neighborhood
) {}