package com.crud.venda.api;

import com.crud.venda.api.model.Response;
import com.crud.venda.application.VendaUseCase;
import com.crud.venda.domain.Venda;
import com.crud.venda.domain.VendaPorCliente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = VendaController.REQUEST_MAPPING)
public class VendaController {

    public static final String REQUEST_MAPPING = "/vendas";

    private final VendaUseCase vendaUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Venda>> criar(@Valid @RequestBody Venda venda) {
        venda = vendaUseCase.criar(venda);
        return ResponseEntity.created(URI.create(REQUEST_MAPPING + "/" + venda.getId())).body(Response.comDado(venda));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<List<Venda>>> consultarTodos() {
        return ResponseEntity.ok(Response.comDado(vendaUseCase.consultarTodos()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Venda>> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(Response.comDado(vendaUseCase.consultarPorId(id)));
    }

    @GetMapping(value = "/cliente/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<VendaPorCliente>> consultarPorCliente(@PathVariable("idCliente") Long idCliente) {
        return ResponseEntity.ok(Response.comDado(vendaUseCase.consultarPorCliente(idCliente)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Venda>> alterar(@PathVariable("id") Long id, @RequestBody Venda venda) {
        return ResponseEntity.ok(Response.comDado(vendaUseCase.alterar(id, venda)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        vendaUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
