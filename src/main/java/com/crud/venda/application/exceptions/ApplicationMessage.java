package com.crud.venda.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationMessage {

    CLIENTE_NAO_EXISTENTE("cliente.nao-existe"), PRODUTO_NAO_EXISTENTE("produto.nao-existe"), VENDA_NAO_EXISTENTE("venda.nao-existe");

    private String codigo;
}
