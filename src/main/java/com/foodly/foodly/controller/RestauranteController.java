package com.foodly.foodly.controller;

import com.foodly.foodly.dto.RestauranteDTO;
import com.foodly.foodly.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "http://localhost:4200")
public class RestauranteController {

    private final RestauranteService service;

    public RestauranteController(RestauranteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RestauranteDTO.Response>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/meus")
    public ResponseEntity<List<RestauranteDTO.Response>> listarMeus() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(service.listarDoOwner(email));
    }

    @PostMapping
    public ResponseEntity<RestauranteDTO.Response> criar(@RequestBody @Valid RestauranteDTO.Request dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(201).body(service.criar(dto, email));
    }
}