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
    public static final String ENDERECO_CLIENTE = "Endereço";

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
    public static final long ID_VENDA_2 = 2L;

    // CLIENTE
    public static Cliente getCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(ID_CLIENTE);
        cliente.setNome(NOME_CLIENTE);
        cliente.setCpf(CPF_CLIENTE);
        cliente.setEndereco(ENDERECO_CLIENTE);
        return cliente;
    }

    public static Cliente getCliente2() {
        Cliente cliente = new Cliente();
        cliente.setId(ID_CLIENTE_2);
        cliente.setNome(NOME_CLIENTE_2);
        cliente.setCpf(CPF_CLIENTE_2);
        cliente.setEndereco(ENDERECO_CLIENTE);
        return cliente;
    }

    public static List<Cliente> getClientes() {
        return List.of(getCliente(), getCliente2());
    }

    // CLIENTE ENTITY
    public static ClienteEntity getClienteEntity() {
        return ClienteEntity.builder().id(ID_CLIENTE).nome(NOME_CLIENTE).cpf(CPF_CLIENTE).endereco(ENDERECO_CLIENTE).build();
    }

    public static ClienteEntity getClienteEntity2() {
        return ClienteEntity.builder().id(ID_CLIENTE_2).nome(NOME_CLIENTE_2).cpf(CPF_CLIENTE_2).endereco(ENDERECO_CLIENTE).build();
    }

    public static List<ClienteEntity> getClientesEntity() {
        return List.of(getClienteEntity(),
                getClienteEntity2());
    }

    // PRODUTO
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

    public static List<Produto> getProdutos() {
        return List.of(getProduto(), getProduto2());
    }

    // PRODUTO ENTITY
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

    // VENDA
    public static Venda getVenda() {
        Venda venda = new Venda();
        venda.setId(ID_VENDA);
        venda.setCliente(getCliente());
        venda.setProdutos(getProdutos());
        venda.setDesconto(0D);
        return venda;
    }

    public static Venda getVenda2() {
        Venda venda = new Venda();
        venda.setId(ID_VENDA_2);
        venda.setCliente(getCliente2());
        venda.setProdutos(getProdutos());
        venda.setDesconto(0D);
        return venda;
    }

    public static List<Venda> getVendas() {
        return List.of(getVenda(), getVenda2());
    }

    // VENDA ENTITY
    public static VendaEntity getVendaEntity() {
        List<ProdutoEntity> produtosEntity = getProdutosEntity();

        return VendaEntity.builder()
                .id(ID_VENDA)
                .cliente(getClienteEntity())
                .produtos(produtosEntity)
                .desconto(0D)
                .valorTotal(produtosEntity.get(0).getValor().add(produtosEntity.get(1).getValor()))
                .valorFinal(produtosEntity.get(0).getValor().add(produtosEntity.get(1).getValor()))
                .build();
    }

    public static VendaEntity getVendaEntity2() {
        List<ProdutoEntity> produtosEntity = getProdutosEntity();

        return VendaEntity.builder()
                .id(ID_VENDA_2)
                .cliente(getClienteEntity2())
                .produtos(produtosEntity)
                .desconto(0D)
                .valorTotal(produtosEntity.get(0).getValor().add(produtosEntity.get(1).getValor()))
                .valorFinal(produtosEntity.get(0).getValor().add(produtosEntity.get(1).getValor()))
                .build();
    }

    public static List<VendaEntity> getVendasEntity() {
        return List.of(getVendaEntity(), getVendaEntity2());
    }
}
