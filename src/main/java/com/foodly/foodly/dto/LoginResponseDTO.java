package com.foodly.foodly.dto;

public record LoginResponseDTO(
        String token,
        String nome,
        String email,
        String role
) {}