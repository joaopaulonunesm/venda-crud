package com.crud.venda.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;
    private List<Erro> errors;

    private Response(T data) {
        this.data = data;
    }

    private Response(List<Erro> errors) {
        this.errors = errors;
    }

    public static Response comDado(Object dado){
        return new Response(dado);
    }

    public static Response comErro(Erro erro){
        List<Erro> erros = List.of(erro);
        return new Response(erros);
    }

    public static Response comErros(List<Erro> erros){
        return new Response(erros);
    }
}
