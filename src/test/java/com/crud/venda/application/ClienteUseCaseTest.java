package com.crud.venda.application;

import com.crud.venda.infrastructure.database.entities.converter.Converter;
import com.crud.venda.domain.Cliente;
import com.crud.venda.infrastructure.database.entities.ClienteEntity;
import com.crud.venda.infrastructure.database.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteUseCaseTest {

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private Converter<ClienteEntity, Cliente> converter;

    @Autowired
    private ClienteUseCase clienteUseCase;

    @Test
    public void testCriarCliente() {
        Cliente cliente = new Cliente();
        ClienteEntity clienteEntity = new ClienteEntity();
        when(converter.toEntity(cliente)).thenReturn(clienteEntity);
        when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        when(converter.toDomain(clienteEntity)).thenReturn(cliente);

        Cliente result = clienteUseCase.criar(cliente);

        verify(converter, times(1)).toEntity(cliente);
        verify(clienteRepository, times(1)).save(clienteEntity);
        verify(converter, times(1)).toDomain(clienteEntity);
        assertEquals(cliente, result);
    }

    @Test
    public void testAlterarCliente() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        ClienteEntity clienteEntity = new ClienteEntity();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteEntity));
        when(converter.toEntity(cliente)).thenReturn(clienteEntity);
        when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        when(converter.toDomain(clienteEntity)).thenReturn(cliente);

        Cliente result = clienteUseCase.alterar(id, cliente);

        verify(clienteRepository, times(1)).findById(id);
        verify(converter, times(1)).toEntity(cliente);
        verify(clienteRepository, times(1)).save(clienteEntity);
        verify(converter, times(2)).toDomain(clienteEntity);
        assertEquals(cliente, result);
    }

    @Test
    public void testConsultarTodosClientes() {
        List<ClienteEntity> clienteEntities = Arrays.asList(new ClienteEntity(), new ClienteEntity());
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(clienteEntities);
        when(converter.toDomains(clienteEntities)).thenReturn(clientes);

        List<Cliente> result = clienteUseCase.consultarTodos();

        verify(clienteRepository, times(1)).findAll();
        verify(converter, times(1)).toDomains(clienteEntities);
        assertEquals(clientes, result);
    }

    @Test
    public void testConsultarClientePorId() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        ClienteEntity clienteEntity = new ClienteEntity();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteEntity));
        when(converter.toDomain(clienteEntity)).thenReturn(cliente);
        Cliente result = clienteUseCase.consultarPorId(id);

        verify(clienteRepository, times(1)).findById(id);
        verify(converter, times(1)).toDomain(clienteEntity);
        assertEquals(cliente, result);
    }

    @Test
    public void testConsultarClientePorIdNaoExistente() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> clienteUseCase.consultarPorId(id));

        verify(clienteRepository, times(1)).findById(id);
        assertEquals("Cliente não existe", exception.getMessage());
    }

    @Test
    public void testDeletarCliente() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        ClienteEntity clienteEntity = new ClienteEntity();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteEntity));
        when(converter.toDomain(clienteEntity)).thenReturn(cliente);

        clienteUseCase.deletar(id);

        verify(clienteRepository, times(1)).findById(id);
        verify(converter, times(1)).toDomain(clienteEntity);
        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletarClienteComVendasAssociadas() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        ClienteEntity clienteEntity = new ClienteEntity();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteEntity));
        when(converter.toDomain(clienteEntity)).thenReturn(cliente);
        doThrow(DataIntegrityViolationException.class).when(clienteRepository).deleteById(id);

        Throwable exception = assertThrows(RuntimeException.class, () -> clienteUseCase.deletar(id));

        verify(clienteRepository, times(1)).findById(id);
        verify(converter, times(1)).toDomain(clienteEntity);
        verify(clienteRepository, times(1)).deleteById(id);
        assertEquals("Cliente não pode ser deletado pois já existe vendas associado a ele.", exception.getMessage());
    }
}
