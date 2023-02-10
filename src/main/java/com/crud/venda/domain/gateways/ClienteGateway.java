package com.crud.venda.domain.gateways;

import com.crud.venda.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteGateway {
    Cliente salvar(Cliente cliente);
    List<Cliente> buscarTodos();
    Optional<Cliente> buscarPorId(Long id);
    void deletarPorId(Long id);
}
