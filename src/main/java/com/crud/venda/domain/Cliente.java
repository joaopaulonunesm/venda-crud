package com.crud.venda.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    private Long id;
    private String nome;
    private String endereco;
    private String cpf;
}
