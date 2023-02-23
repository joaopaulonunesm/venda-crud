package com.crud.venda.application;

import com.crud.venda.application.exceptions.ApplicationException;
import com.crud.venda.application.exceptions.ApplicationMessage;
import com.crud.venda.domain.Produto;
import com.crud.venda.domain.gateways.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public Produto criar(Produto produto) {
        return produtoGateway.salvar(produto);
    }

    public Produto alterar(Long id, Produto produto) {
        consultarPorId(id);
        produto.setId(id);
        return produtoGateway.salvar(produto);
    }

    public List<Produto> consultarTodos() {
        return produtoGateway.buscarTodos();
    }

    public Produto consultarPorId(Long id) {
        return produtoGateway.buscarPorId(id).orElseThrow(() -> new ApplicationException(ApplicationMessage.PRODUTO_NAO_EXISTENTE, id));
    }

    public void deletar(Long id) {
        consultarPorId(id);
        produtoGateway.deletarPorId(id);
    }
}
