package com.crud.venda.api;

import com.crud.venda.api.model.Response;
import com.crud.venda.application.ProdutoUseCase;
import com.crud.venda.domain.Produto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ProdutoController.REQUEST_MAPPING)
public class ProdutoController {

    public static final String REQUEST_MAPPING = "/produtos";

    private final ProdutoUseCase produtoUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Produto>> criar(@Valid @RequestBody Produto produto) {
        produto = produtoUseCase.criar(produto);
        return ResponseEntity.created(URI.create(REQUEST_MAPPING + "/" + produto.getId())).body(Response.comDado(produto));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<List<Produto>>> consultarTodos() {
        return ResponseEntity.ok(Response.comDado(produtoUseCase.consultarTodos()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Produto>> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.comDado(produtoUseCase.consultarPorId(id)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Produto>> alterar(@PathVariable("id") Long id, @RequestBody Produto produto) {
        return ResponseEntity.ok(Response.comDado(produtoUseCase.alterar(id, produto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        produtoUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}