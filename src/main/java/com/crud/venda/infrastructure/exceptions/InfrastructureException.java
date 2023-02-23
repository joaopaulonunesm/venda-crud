package com.crud.venda.infrastructure.exceptions;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class InfrastructureException extends RuntimeException {

    private String codigo;
    private Object[] parametros;

    public InfrastructureException(InfrastructureMessage message, Object... parametros) {
        super(message.name() + " - " + Arrays.stream(parametros).toList());
        this.codigo = message.getCodigo();
        this.parametros = parametros;
    }
}
