package com.crud.venda.api;

import com.crud.venda.domain.Cliente;
import com.crud.venda.application.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente criar(@RequestBody Cliente Cliente) {
        return clienteUseCase.criar(Cliente);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cliente> consultarTodos() {
        return clienteUseCase.consultarTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente consultarPorId(@PathVariable("id") Long id) {
        return clienteUseCase.consultarPorId(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente alterar(@PathVariable("id") Long id, @RequestBody Cliente Cliente) {
        return clienteUseCase.alterar(id, Cliente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id) {
        clienteUseCase.deletar(id);
    }
}
