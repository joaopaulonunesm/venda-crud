package com.crud.venda.domain;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Produto {

    private Long id;
    @NotBlank(message = "{error.preenchimento-not-blank.nome}")
    private String nome;
    @NotNull(message = "{error.preenchimento-not-null.valor}")
    @PositiveOrZero(message = "{error.preenchimento-positive-or-zero.valor}")
    @Digits(integer = 11, fraction = 2, message = "{error.preenchimento-digits.valor}")
    private BigDecimal valor;
    @NotNull(message = "{error.preenchimento-not-null.quantidade}")
    @PositiveOrZero(message = "{error.preenchimento-positive-or-zero.quantidade}")
    private Integer quantidade;
}
