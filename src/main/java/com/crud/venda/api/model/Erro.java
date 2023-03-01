package com.crud.venda.api.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Erro {

    private String codigo;
    private String mensagem;
}
