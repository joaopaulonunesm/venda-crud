package com.crud.venda.models.dto;

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
    private BigDecimal valorTotal;
    private Double desconto;
    private BigDecimal valorFinal;
    private List<Produto> produtos;
    private Cliente cliente;
}
