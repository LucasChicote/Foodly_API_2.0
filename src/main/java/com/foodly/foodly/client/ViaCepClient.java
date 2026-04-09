package com.foodly.foodly.client;

import com.foodly.foodly.model.Endereco;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.bind.annotation.PathVariable;

@HttpExchange("https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetExchange("/{cep}/json")
    Endereco buscarCep( @PathVariable String cep );
}