package com.crud.venda.application;

import com.crud.venda.application.exceptions.ApplicationException;
import com.crud.venda.application.exceptions.ApplicationMessage;
import com.crud.venda.domain.Cliente;
import com.crud.venda.domain.Produto;
import com.crud.venda.domain.Venda;
import com.crud.venda.domain.VendaPorCliente;
import com.crud.venda.domain.gateways.VendaGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VendaUseCase {

    private final VendaGateway vendaGateway;

    private final ClienteUseCase clienteUseCase;
    private final ProdutoUseCase produtoUseCase;

    public Venda criar(Venda venda) {
        Cliente cliente = clienteUseCase.consultarPorId(venda.getCliente().getId());
        venda.setCliente(cliente);

        List<Produto> produtos = venda.getProdutos().stream().map(produto -> produtoUseCase.consultarPorId(produto.getId())).toList();
        venda.setProdutos(produtos);

        calculaValorTotalEFinal(venda);

        return vendaGateway.salvar(venda);
    }

    public Venda alterar(Long id, Venda venda) {
        consultarPorId(id);
        venda.setId(id);

        Cliente cliente = clienteUseCase.consultarPorId(venda.getCliente().getId());
        venda.setCliente(cliente);

        List<Produto> produtos = venda.getProdutos().stream().map(produto -> produtoUseCase.consultarPorId(produto.getId())).toList();
        venda.setProdutos(produtos);

        calculaValorTotalEFinal(venda);

        return vendaGateway.salvar(venda);
    }

    public List<Venda> consultarTodos() {
        return vendaGateway.buscarTodos();
    }

    public Venda consultarPorId(Long id) {
        return vendaGateway.buscarPorId(id)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.VENDA_NAO_EXISTENTE, id));
    }

    public VendaPorCliente consultarPorCliente(Long idCliente) {
        clienteUseCase.consultarPorId(idCliente);
        List<Venda> vendas = vendaGateway.buscarPorCliente(idCliente);
        return new VendaPorCliente(vendas);
    }

    public void deletar(Long id) {
        consultarPorId(id);
        vendaGateway.deletarPorId(id);
    }

    private static void calculaValorTotalEFinal(Venda venda) {
        BigDecimal valorTotalProdutos = venda.getProdutos().stream()
                .map(Produto::getValor)
                .reduce(BigDecimal::add).get();
        venda.setValorTotal(valorTotalProdutos);

        BigDecimal valorDesconto = valorTotalProdutos.multiply(BigDecimal.valueOf(venda.getDesconto() == null ? 0 : venda.getDesconto() / 100));
        venda.setValorFinal(valorTotalProdutos.subtract(valorDesconto));
    }


}
