package com.crud.venda.application;

import com.crud.venda.infrastructure.database.entities.converter.Converter;
import com.crud.venda.domain.Cliente;
import com.crud.venda.domain.Produto;
import com.crud.venda.domain.Venda;
import com.crud.venda.infrastructure.database.entities.ClienteEntity;
import com.crud.venda.infrastructure.database.entities.ProdutoEntity;
import com.crud.venda.infrastructure.database.entities.VendaEntity;
import com.crud.venda.infrastructure.database.repositories.VendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VendaUseCaseTest {

    @MockBean
    private VendaRepository vendaRepository;

    @MockBean
    private ClienteUseCase clienteUseCase;

    @MockBean
    private ProdutoUseCase produtoUseCase;

    @MockBean
    private Converter<VendaEntity, Venda> converter;

    @Autowired
    private VendaUseCase vendaUseCase;

    @Test
    void testCriarVenda() {
        Venda venda = new Venda();
        venda.setProdutos(Arrays.asList(new Produto(), new Produto()));
        venda.setCliente(new Cliente());
        VendaEntity vendaEntity = new VendaEntity();
        vendaEntity.setProdutos(Arrays.asList(new ProdutoEntity(), new ProdutoEntity()));
        vendaEntity.setCliente(new ClienteEntity());

        when(converter.toEntity(venda)).thenReturn(vendaEntity);
        when(vendaRepository.save(vendaEntity)).thenReturn(vendaEntity);
        when(converter.toDomain(vendaEntity)).thenReturn(venda);

        Venda vendaSalvo = vendaUseCase.criar(venda);

        verify(converter, times(1)).toEntity(venda);
        verify(vendaRepository, times(1)).save(vendaEntity);
        verify(converter, times(1)).toDomain(vendaEntity);
        assertThat(vendaSalvo).isEqualTo(venda);
    }

    @Test
    void testAlterarVenda() {
        Venda venda = new Venda();
        venda.setProdutos(Arrays.asList(new Produto(), new Produto()));
        venda.setCliente(new Cliente());
        VendaEntity vendaEntity = new VendaEntity();
        vendaEntity.setProdutos(Arrays.asList(new ProdutoEntity(), new ProdutoEntity()));
        vendaEntity.setCliente(new ClienteEntity());

        when(converter.toEntity(venda)).thenReturn(vendaEntity);
        when(vendaRepository.save(vendaEntity)).thenReturn(vendaEntity);
        when(converter.toDomain(vendaEntity)).thenReturn(venda);
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(vendaEntity));

        Venda vendaAlterado = vendaUseCase.alterar(1L, venda);

        verify(converter, times(1)).toEntity(venda);
        verify(vendaRepository, times(1)).save(vendaEntity);
        verify(converter, times(2)).toDomain(vendaEntity);
        verify(vendaRepository, times(1)).findById(1L);
        assertThat(vendaAlterado).isEqualTo(venda);
    }

    @Test
    public void testConsultarTodosVendas() {
        List<VendaEntity> vendaEntities = Arrays.asList(new VendaEntity(), new VendaEntity());

        List<Venda> vendas = Arrays.asList(new Venda(), new Venda());

        when(vendaRepository.findAll()).thenReturn(vendaEntities);
        when(converter.toDomains(vendaEntities)).thenReturn(vendas);

        List<Venda> result = vendaUseCase.consultarTodos();

        verify(vendaRepository, times(1)).findAll();
        verify(converter, times(1)).toDomains(vendaEntities);
        assertEquals(vendas, result);
    }

    @Test
    public void testConsultarVendaPorId() {
        Long id = 1L;
        Venda venda = new Venda();
        VendaEntity vendaEntity = new VendaEntity();
        when(vendaRepository.findById(id)).thenReturn(Optional.of(vendaEntity));
        when(converter.toDomain(vendaEntity)).thenReturn(venda);
        Venda result = vendaUseCase.consultarPorId(id);

        verify(vendaRepository, times(1)).findById(id);
        verify(converter, times(1)).toDomain(vendaEntity);
        assertEquals(venda, result);
    }

    @Test
    public void testConsultarVendaPorIdNaoExistente() {
        Long id = 1L;
        when(vendaRepository.findById(id)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> vendaUseCase.consultarPorId(id));

        verify(vendaRepository, times(1)).findById(id);
        assertEquals("Venda não existe", exception.getMessage());
    }

    @Test
    public void testDeletarVenda() {
        Long id = 1L;
        Venda venda = new Venda();
        VendaEntity vendaEntity = new VendaEntity();
        when(vendaRepository.findById(id)).thenReturn(Optional.of(vendaEntity));
        when(converter.toDomain(vendaEntity)).thenReturn(venda);

        vendaUseCase.deletar(id);

        verify(vendaRepository, times(1)).findById(id);
        verify(converter, times(1)).toDomain(vendaEntity);
        verify(vendaRepository, times(1)).deleteById(id);
    }
}
