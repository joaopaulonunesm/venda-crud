package com.crud.venda.models.dto;

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
    private String nome;
    private BigDecimal valor;
    private Integer quantidade;
}
