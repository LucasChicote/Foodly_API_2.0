package com.foodly.foodly.controller;

import com.foodly.foodly.dto.ProdutoResponseDTO;
import com.foodly.foodly.dto.RestauranteDTO;
import com.foodly.foodly.service.ProdutoService;
import com.foodly.foodly.service.RestauranteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;

    public SearchController(ProdutoService produtoService, RestauranteService restauranteService) {
        this.produtoService = produtoService;
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public Map<String, Object> buscar(@RequestParam String termo) {
        Map<String, Object> resultado = new HashMap<>();
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorNomeOuDescricao(termo);
        List<RestauranteDTO.Response> restaurantes = restauranteService.buscarPorNome(termo);
        resultado.put("produtos", produtos);
        resultado.put("restaurantes", restaurantes);
        return resultado;
    }
}