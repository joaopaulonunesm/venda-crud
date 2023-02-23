package com.crud.venda.api;

import com.crud.venda.domain.Produto;
import com.crud.venda.application.ProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    private final ProdutoUseCase produtoUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Produto criar(@RequestBody Produto produto){
        return produtoUseCase.criar(produto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Produto> consultarTodos(){
        return produtoUseCase.consultarTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Produto consultarPorId(@PathVariable("id") Long id){
        return produtoUseCase.consultarPorId(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Produto alterar(@PathVariable("id") Long id, @RequestBody Produto produto){
        return produtoUseCase.alterar(id, produto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id){
        produtoUseCase.deletar(id);
    }
}