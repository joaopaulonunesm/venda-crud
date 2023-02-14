package com.crud.venda.utils;

import com.crud.venda.domain.Cliente;
import com.crud.venda.domain.Produto;
import com.crud.venda.domain.Venda;
import com.crud.venda.infrastructure.database.entities.ClienteEntity;
import com.crud.venda.infrastructure.database.entities.ProdutoEntity;
import com.crud.venda.infrastructure.database.entities.VendaEntity;

import java.math.BigDecimal;
import java.util.List;

public class DataUtils {

    public static final long ID_CLIENTE = 1L;
    public static final String CPF_CLIENTE = "10000000000";
    public static final String NOME_CLIENTE = "João da Silva";

    public static final long ID_CLIENTE_2 = 2L;
    public static final String CPF_CLIENTE_2 = "20000000000";
    public static final String NOME_CLIENTE_2 = "Caio da Silva";


    public static final long ID_PRODUTO = 1L;
    public static final String NOME_PRODUTO = "Nome Produto";
    public static final BigDecimal VALOR_PRODUTO = BigDecimal.valueOf(10.5);
    public static final Integer QUANTIDADE_PRODUTO = 5;
    public static final long ID_PRODUTO_2 = 2L;
    public static final String NOME_PRODUTO_2 = "Nome Produto 2";
    public static final BigDecimal VALOR_PRODUTO_2 = BigDecimal.valueOf(20.5);
    public static final Integer QUANTIDADE_PRODUTO_2 = 10;

    public static final long ID_VENDA = 1L;

    public static Cliente getCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(ID_CLIENTE);
        cliente.setNome(NOME_CLIENTE);
        cliente.setCpf(CPF_CLIENTE);
        return cliente;
    }

    public static Cliente getCliente2() {
        Cliente cliente = new Cliente();
        cliente.setId(ID_CLIENTE_2);
        cliente.setNome(NOME_CLIENTE_2);
        cliente.setCpf(CPF_CLIENTE_2);
        return cliente;
    }

    public static ClienteEntity getClienteEntity() {
        return ClienteEntity.builder().id(ID_CLIENTE).nome(NOME_CLIENTE).cpf(CPF_CLIENTE).build();
    }

    public static ClienteEntity getClienteEntity2() {
        return ClienteEntity.builder().id(ID_CLIENTE_2).nome(NOME_CLIENTE_2).cpf(CPF_CLIENTE_2).build();
    }

    public static List<ClienteEntity> getClientesEntity() {
        return List.of(getClienteEntity(),
                getClienteEntity2());
    }

    public static Produto getProduto() {
        Produto produtoRequest = new Produto();
        produtoRequest.setId(ID_PRODUTO);
        produtoRequest.setNome(NOME_PRODUTO);
        produtoRequest.setValor(VALOR_PRODUTO);
        produtoRequest.setQuantidade(QUANTIDADE_PRODUTO);
        return produtoRequest;
    }

    public static Produto getProduto2() {
        Produto produtoRequest = new Produto();
        produtoRequest.setId(ID_PRODUTO_2);
        produtoRequest.setNome(NOME_PRODUTO_2);
        produtoRequest.setValor(VALOR_PRODUTO_2);
        produtoRequest.setQuantidade(QUANTIDADE_PRODUTO_2);
        return produtoRequest;
    }

    public static ProdutoEntity getProdutoEntity() {
        return ProdutoEntity.builder().id(ID_PRODUTO).nome(NOME_PRODUTO).valor(VALOR_PRODUTO).quantidade(QUANTIDADE_PRODUTO).build();
    }

    public static ProdutoEntity getProdutoEntity2() {
        return ProdutoEntity.builder().id(ID_PRODUTO_2).nome(NOME_PRODUTO_2).valor(VALOR_PRODUTO_2).quantidade(QUANTIDADE_PRODUTO_2).build();
    }

    public static List<ProdutoEntity> getProdutosEntity() {
        return List.of(getProdutoEntity(),
                getProdutoEntity2());
    }

    public static Venda getVenda() {
        Venda venda = new Venda();
        venda.setId(ID_VENDA);
        venda.setCliente(getCliente());
        venda.setProdutos(List.of(getProduto()));
        return venda;
    }

    public static VendaEntity getVendaEntity() {
        List<ProdutoEntity> produtosEntity = List.of(getProdutoEntity());
        BigDecimal valorTotalProdutos = produtosEntity.stream().map(ProdutoEntity::getValor).reduce(BigDecimal::add).get();

        return VendaEntity.builder()
                .id(ID_VENDA)
                .cliente(getClienteEntity())
                .produtos(produtosEntity)
                .desconto(0D)
                .valorTotal(valorTotalProdutos)
                .valorFinal(valorTotalProdutos)
                .build();
    }

    public static List<VendaEntity> getVendasEntity() {
        return List.of(getVendaEntity());
    }
}
