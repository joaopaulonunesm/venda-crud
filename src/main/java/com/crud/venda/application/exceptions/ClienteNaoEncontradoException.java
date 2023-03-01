package com.crud.venda.application.exceptions;

import lombok.Getter;

@Getter
public class ClienteNaoEncontradoException extends ApplicationException {

    public ClienteNaoEncontradoException(ApplicationMessage message, Object... parametros) {
        super(message, parametros);
    }
}
