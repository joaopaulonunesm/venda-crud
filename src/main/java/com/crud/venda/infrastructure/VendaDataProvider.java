package com.crud.venda.infrastructure;

import com.crud.venda.infrastructure.database.entities.converter.Converter;
import com.crud.venda.infrastructure.database.entities.VendaEntity;
import com.crud.venda.infrastructure.database.repositories.VendaRepository;
import com.crud.venda.domain.Venda;
import com.crud.venda.domain.gateways.VendaGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VendaDataProvider implements VendaGateway {

    private final Converter<VendaEntity, Venda> converter;

    private final VendaRepository vendaRepository;

    @Override
    public Venda salvar(Venda venda) {
        return converter.toDomain(vendaRepository.save(converter.toEntity(venda)));
    }

    @Override
    public List<Venda> buscarTodos() {
        return converter.toDomains(vendaRepository.findAll());
    }

    @Override
    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository
                .findById(id)
                .map(venda -> Optional.of(converter.toDomain(venda)))
                .orElse(Optional.empty());
    }

    @Override
    public void deletarPorId(Long id) {
        vendaRepository.deleteById(id);
    }
}
