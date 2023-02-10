package com.crud.venda.infrastructure;

import com.crud.venda.infrastructure.database.entities.converter.Converter;
import com.crud.venda.infrastructure.database.entities.ClienteEntity;
import com.crud.venda.infrastructure.database.repositories.ClienteRepository;
import com.crud.venda.domain.Cliente;
import com.crud.venda.domain.gateways.ClienteGateway;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ClienteDataProvider implements ClienteGateway {

    private final Converter<ClienteEntity, Cliente> converter;

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente salvar(Cliente cliente) {
        return converter.toDomain(clienteRepository.save(converter.toEntity(cliente)));
    }

    @Override
    public List<Cliente> buscarTodos() {
        return converter.toDomains(clienteRepository.findAll());
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository
                .findById(id)
                .map(cliente -> Optional.of(converter.toDomain(cliente)))
                .orElse(Optional.empty());
    }

    @Override
    public void deletarPorId(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Cliente não pode ser deletado pois já existe vendas associado a ele.");
        }
    }
}
