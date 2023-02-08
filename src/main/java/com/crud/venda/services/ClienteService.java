package com.crud.venda.services;

import com.crud.venda.models.converter.Converter;
import com.crud.venda.models.dto.Cliente;
import com.crud.venda.models.entity.ClienteEntity;
import com.crud.venda.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final Converter<ClienteEntity, Cliente> converter;

    public Cliente criar(Cliente cliente) {
        return converter.toDomain(clienteRepository.save(converter.toEntity(cliente)));
    }

    public Cliente alterar(Long id, Cliente cliente) {
        consultarPorId(id);
        cliente.setId(id);
        return converter.toDomain(clienteRepository.save(converter.toEntity(cliente)));
    }

    public List<Cliente> consultarTodos() {
        return converter.toDomains(clienteRepository.findAll());
    }

    public Cliente consultarPorId(Long id) {
        return converter.toDomain(clienteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não existe")));
    }

    public void deletar(Long id) {
        consultarPorId(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Clente não pode ser deletado pois já existe vendas associado a ele.");
        }
    }
}
