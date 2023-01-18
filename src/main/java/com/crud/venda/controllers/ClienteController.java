package com.crud.venda.controllers;

import com.crud.venda.models.dto.Cliente;
import com.crud.venda.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente criar(@RequestBody Cliente Cliente) {
        return clienteService.criar(Cliente);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cliente> consultarTodos() {
        return clienteService.consultarTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente consultarPorId(@PathVariable("id") Long id) {
        return clienteService.consultarPorId(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente alterar(@PathVariable("id") Long id, @RequestBody Cliente Cliente) {
        return clienteService.alterar(id, Cliente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id) {
        clienteService.deletar(id);
    }
}
