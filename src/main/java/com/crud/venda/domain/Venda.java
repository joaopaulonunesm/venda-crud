package com.crud.venda.domain;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Venda {

    private Long id;
    @PositiveOrZero(message = "{error.preenchimento-positive-or-zero.desconto}")
    private Double desconto;
    private List<Produto> produtos;
    private Cliente cliente;

    private BigDecimal valorTotal;
    private BigDecimal valorFinal;
}
