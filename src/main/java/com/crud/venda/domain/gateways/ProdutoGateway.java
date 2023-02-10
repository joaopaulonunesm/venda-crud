package com.crud.venda.domain.gateways;

import com.crud.venda.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoGateway {
    Produto salvar(Produto produto);
    List<Produto> buscarTodos();
    Optional<Produto> buscarPorId(Long id);
    void deletarPorId(Long id);
}
