package com.crud.venda.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InfrastructureMessage {

    CLIENTE_JA_POSSUI_VENDA("cliente.ja-possui-venda"),
    PRODUTO_JA_POSSUI_VENDA("produto.ja-possui-venda");

    private String codigo;
}
