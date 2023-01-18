package com.crud.venda.controllers;

import com.crud.venda.models.dto.Produto;
import com.crud.venda.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Produto criar(@RequestBody Produto produto){
        return produtoService.criar(produto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Produto> consultarTodos(){
        return produtoService.consultarTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Produto consultarPorId(@PathVariable("id") Long id){
        return produtoService.consultarPorId(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Produto alterar(@PathVariable("id") Long id, @RequestBody Produto produto){
        return produtoService.alterar(id, produto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id){
        produtoService.deletar(id);
    }
}
