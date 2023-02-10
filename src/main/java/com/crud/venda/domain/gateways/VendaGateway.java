package com.crud.venda.domain.gateways;

import com.crud.venda.domain.Venda;

import java.util.List;
import java.util.Optional;

public interface VendaGateway {
    Venda salvar(Venda venda);
    List<Venda> buscarTodos();
    Optional<Venda> buscarPorId(Long id);
    void deletarPorId(Long id);
}
