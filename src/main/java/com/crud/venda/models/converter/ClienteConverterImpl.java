package com.crud.venda.models.converter;

import com.crud.venda.models.dto.Cliente;
import com.crud.venda.models.entity.ClienteEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteConverterImpl implements Converter<ClienteEntity, Cliente> {

    @Override
    public ClienteEntity toEntity(Cliente domain) {
        return ClienteEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .endereco(domain.getEndereco())
                .cpf(domain.getCpf())
                .dataNascimento(domain.getDataNascimento())
                .build();
    }

    @Override
    public Cliente toDomain(ClienteEntity entity) {
        return Cliente.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .endereco(entity.getEndereco())
                .cpf(entity.getCpf())
                .dataNascimento(entity.getDataNascimento())
                .build();
    }
}
