package com.crud.venda.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "venda")
public class VendaEntity {

    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "VENDASEQ", sequenceName = "VENDA_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "VENDASEQ")
    private Long id;
    private BigDecimal valorTotal;
    private Double desconto;
    private BigDecimal valorFinal;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "venda_produtos",
            joinColumns = {@JoinColumn(name = "id_venda")},
            inverseJoinColumns = {@JoinColumn(name = "id_produto")})
    private List<ProdutoEntity> produtos;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;
}
