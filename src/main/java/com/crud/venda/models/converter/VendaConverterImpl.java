package com.crud.venda.models.converter;

import com.crud.venda.models.dto.Cliente;
import com.crud.venda.models.dto.Produto;
import com.crud.venda.models.dto.Venda;
import com.crud.venda.models.entity.ClienteEntity;
import com.crud.venda.models.entity.ProdutoEntity;
import com.crud.venda.models.entity.VendaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendaConverterImpl implements Converter<VendaEntity, Venda> {

    private final Converter<ProdutoEntity, Produto> produtoConverter;
    private final Converter<ClienteEntity, Cliente> clienteConverter;

    @Override
    public VendaEntity toEntity(Venda domain) {
        return VendaEntity.builder()
                .id(domain.getId())
                .valorTotal(domain.getValorTotal())
                .desconto(domain.getDesconto())
                .valorFinal(domain.getValorFinal())
                .produtos(produtoConverter.toEntities(domain.getProdutos()))
                .cliente(clienteConverter.toEntity(domain.getCliente()))
                .build();
    }

    @Override
    public Venda toDomain(VendaEntity entity) {
        return Venda.builder()
                .id(entity.getId())
                .valorTotal(entity.getValorTotal())
                .desconto(entity.getDesconto())
                .valorFinal(entity.getValorFinal())
                .produtos(produtoConverter.toDomains(entity.getProdutos()))
                .cliente(clienteConverter.toDomain(entity.getCliente()))
                .build();
    }
}
