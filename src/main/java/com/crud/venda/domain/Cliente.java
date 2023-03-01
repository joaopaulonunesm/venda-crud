package com.crud.venda.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    private Long id;
    @NotBlank(message = "{error.preenchimento-not-blank.nome}")
    private String nome;
    @NotBlank(message = "{error.preenchimento-not-blank.endereco}")
    private String endereco;
    @CPF
    private String cpf;
}
