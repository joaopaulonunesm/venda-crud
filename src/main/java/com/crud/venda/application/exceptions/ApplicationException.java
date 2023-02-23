package com.crud.venda.application.exceptions;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class ApplicationException extends RuntimeException {

    private String codigo;
    private Object[] parametros;

    public ApplicationException(ApplicationMessage message, Object... parametros) {
        super(message.name() + " - " + Arrays.stream(parametros).toList());
        this.codigo = message.getCodigo();
        this.parametros = parametros;
    }
}
