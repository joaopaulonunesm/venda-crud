package com.crud.venda.infrastructure.database.entities.converter;

import com.crud.venda.domain.Produto;
import com.crud.venda.infrastructure.database.entities.ProdutoEntity;
import org.springframework.stereotype.Service;

@Service
public class ProdutoConverterImpl implements Converter<ProdutoEntity, Produto> {

    @Override
    public ProdutoEntity toEntity(Produto domain) {
        return ProdutoEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .valor(domain.getValor())
                .quantidade(domain.getQuantidade())
                .build();
    }

    @Override
    public Produto toDomain(ProdutoEntity entity) {
        return Produto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .valor(entity.getValor())
                .quantidade(entity.getQuantidade())
                .build();
    }
}
