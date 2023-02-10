package com.crud.venda.api;

import com.crud.venda.domain.Venda;
import com.crud.venda.application.VendaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/vendas")
public class VendaController {

    private final VendaUseCase vendaUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Venda criar(@RequestBody Venda venda){
        return vendaUseCase.criar(venda);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Venda> consultarTodos(){
        return vendaUseCase.consultarTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Venda consultarPorId(@PathVariable("id") Long id){
        return vendaUseCase.consultarPorId(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Venda alterar(@PathVariable("id") Long id, @RequestBody Venda venda){
        return vendaUseCase.alterar(id, venda);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id){
        vendaUseCase.deletar(id);
    }
}
