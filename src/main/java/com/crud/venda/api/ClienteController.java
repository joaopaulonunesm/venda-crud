package com.crud.venda.api;

import com.crud.venda.api.model.Response;
import com.crud.venda.application.ClienteUseCase;
import com.crud.venda.domain.Cliente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ClienteController.REQUEST_MAPPING)
public class ClienteController {

    public static final String REQUEST_MAPPING = "/clientes";

    private final ClienteUseCase clienteUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Cliente>> criar(@Valid @RequestBody Cliente cliente) {
        cliente = clienteUseCase.criar(cliente);
        return ResponseEntity.created(URI.create(REQUEST_MAPPING + "/" + cliente.getId()))
                .body(Response.comDado(cliente));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<List<Cliente>>> consultarTodos() {
        return ResponseEntity.ok(Response.comDado(clienteUseCase.consultarTodos()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Cliente>> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.comDado(clienteUseCase.consultarPorId(id)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Cliente>> alterar(@PathVariable("id") Long id, @RequestBody Cliente Cliente) {
        return ResponseEntity.ok(Response.comDado(clienteUseCase.alterar(id, Cliente)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        clienteUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
