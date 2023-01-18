package com.crud.venda.controllers;

import com.crud.venda.models.dto.Venda;
import com.crud.venda.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Venda criar(@RequestBody Venda venda){
        return vendaService.criar(venda);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Venda> consultarTodos(){
        return vendaService.consultarTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Venda consultarPorId(@PathVariable("id") Long id){
        return vendaService.consultarPorId(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Venda alterar(@PathVariable("id") Long id, @RequestBody Venda venda){
        return vendaService.alterar(id, venda);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id){
        vendaService.deletar(id);
    }
}
