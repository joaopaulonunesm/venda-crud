package com.crud.venda.models.dto;

import lombok.*;

import java.time.LocalDate;

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
    private LocalDate dataNascimento;
}
