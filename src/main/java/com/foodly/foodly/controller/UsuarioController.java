package com.foodly.foodly.controller;

import com.foodly.foodly.dto.UsuarioResponseDTO;
import com.foodly.foodly.model.Endereco;
import com.foodly.foodly.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Endereco> buscarCep(@PathVariable String cep) {
        return ResponseEntity.ok(service.buscarCep(cep));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}