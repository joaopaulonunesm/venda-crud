package com.crud.venda.application;

import com.crud.venda.infrastructure.database.entities.converter.Converter;
import com.crud.venda.domain.Produto;
import com.crud.venda.infrastructure.database.entities.ProdutoEntity;
import com.crud.venda.infrastructure.database.repositories.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProdutoUseCaseTest {

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private Converter<ProdutoEntity, Produto> converter;

    @Autowired
    private ProdutoUseCase produtoUseCase;

    @Test
    void testCriarProduto() {
        Produto produto = new Produto();
        ProdutoEntity produtoEntity = new ProdutoEntity();

        when(converter.toEntity(produto)).thenReturn(produtoEntity);
        when(produtoRepository.save(produtoEntity)).thenReturn(produtoEntity);
        when(converter.toDomain(produtoEntity)).thenReturn(produto);

        Produto produtoSalvo = produtoUseCase.criar(produto);

        verify(converter, times(1)).toEntity(produto);
        verify(produtoRepository, times(1)).save(produtoEntity);
        verify(converter, times(1)).toDomain(produtoEntity);
        assertThat(produtoSalvo).isEqualTo(produto);
    }

    @Test
    void testAlterarProduto() {
        Produto produto = new Produto();
        ProdutoEntity produtoEntity = new ProdutoEntity();

        when(converter.toEntity(produto)).thenReturn(produtoEntity);
        when(produtoRepository.save(produtoEntity)).thenReturn(produtoEntity);
        when(converter.toDomain(produtoEntity)).thenReturn(produto);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoEntity));

        Produto produtoAlterado = produtoUseCase.alterar(1L, produto);

        verify(converter, times(1)).toEntity(produto);
        verify(produtoRepository, times(1)).save(produtoEntity);
        verify(converter, times(2)).toDomain(produtoEntity);
        verify(produtoRepository, times(1)).findById(1L);
        assertThat(produtoAlterado).isEqualTo(produto);
    }

    @Test
    public void testConsultarTodosProdutos() {
        List<ProdutoEntity> produtoEntities = Arrays.asList(
                ProdutoEntity.builder().id(1L).nome("Produto 1").valor(new BigDecimal("10.00")).build(),
                ProdutoEntity.builder().id(2L).nome("Produto 2").valor(new BigDecimal("20.00")).build(),
                ProdutoEntity.builder().id(3L).nome("Produto 3").valor(new BigDecimal("30.00")).build());

        List<Produto> produtos = Arrays.asList(
                Produto.builder().id(1L).nome("Produto 1").valor(new BigDecimal("10.00")).build(),
                Produto.builder().id(2L).nome("Produto 2").valor(new BigDecimal("20.00")).build(),
                Produto.builder().id(3L).nome("Produto 3").valor(new BigDecimal("30.00")).build());

        when(produtoRepository.findAll()).thenReturn(produtoEntities);
        when(converter.toDomains(produtoEntities)).thenReturn(produtos);

        List<Produto> result = produtoUseCase.consultarTodos();

        verify(produtoRepository, times(1)).findAll();
        verify(converter, times(1)).toDomains(produtoEntities);
        assertEquals(produtos, result);
    }

    @Test
    public void testConsultarProdutoPorId() {
        Long id = 1L;
        Produto produto = new Produto();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEntity));
        when(converter.toDomain(produtoEntity)).thenReturn(produto);
        Produto result = produtoUseCase.consultarPorId(id);

        verify(produtoRepository, times(1)).findById(id);
        verify(converter, times(1)).toDomain(produtoEntity);
        assertEquals(produto, result);
    }

    @Test
    public void testConsultarProdutoPorIdNaoExistente() {
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> produtoUseCase.consultarPorId(id));

        verify(produtoRepository, times(1)).findById(id);
        assertEquals("Produto não existe", exception.getMessage());
    }

    @Test
    public void testDeletarProduto() {
        Long id = 1L;
        Produto produto = new Produto();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEntity));
        when(converter.toDomain(produtoEntity)).thenReturn(produto);

        produtoUseCase.deletar(id);

        verify(produtoRepository, times(1)).findById(id);
        verify(converter, times(1)).toDomain(produtoEntity);
        verify(produtoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletarProdutoComVendasAssociadas() {
        Long id = 1L;
        Produto produto = new Produto();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEntity));
        when(converter.toDomain(produtoEntity)).thenReturn(produto);
        doThrow(DataIntegrityViolationException.class).when(produtoRepository).deleteById(id);

        Throwable exception = assertThrows(RuntimeException.class, () -> produtoUseCase.deletar(id));

        verify(produtoRepository, times(1)).findById(id);
        verify(converter, times(1)).toDomain(produtoEntity);
        verify(produtoRepository, times(1)).deleteById(id);
        assertEquals("Produto não pode ser deletado pois já existe vendas associado a ele.", exception.getMessage());
    }
}
