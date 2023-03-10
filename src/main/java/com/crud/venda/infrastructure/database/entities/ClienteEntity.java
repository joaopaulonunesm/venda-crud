package com.crud.venda.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 1, name = "CLIENTESEQ", sequenceName = "CLIENTE_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CLIENTESEQ")
    private Long id;
    private String nome;
    private String endereco;
    private String cpf;
}
