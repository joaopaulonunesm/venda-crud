package com.crud.venda.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class VendaPorCliente {

    private Integer quantidade;
    private BigDecimal valorBruto;
    private BigDecimal valorLiquido;
    private BigDecimal descontosAplicados;
    private List<Venda> vendas;

    public VendaPorCliente(List<Venda> vendas) {
        this.vendas = vendas;
        this.quantidade = vendas.size();
        this.valorBruto = vendas.stream().map(Venda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        this.valorLiquido = vendas.stream().map(Venda::getValorFinal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        this.descontosAplicados = valorBruto.subtract(valorLiquido);
    }
}
