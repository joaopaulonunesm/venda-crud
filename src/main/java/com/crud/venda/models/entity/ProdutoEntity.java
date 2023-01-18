package com.crud.venda.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "produto")
public class ProdutoEntity {

    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "PRODUTOSEQ", sequenceName = "PRODUTO_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUTOSEQ")
    private Long id;
    private String nome;
    private BigDecimal valor;
    private Integer quantidade;
}
