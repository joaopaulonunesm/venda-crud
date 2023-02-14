package com.crud.venda.api;

import com.crud.venda.domain.Produto;
import com.crud.venda.infrastructure.database.entities.ProdutoEntity;
import com.crud.venda.infrastructure.database.repositories.ProdutoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    public static final long ID_PRODUTO = 1L;
    public static final String NOME_PRODUTO = "João da Silva";
    public static final BigDecimal VALOR_PRODUTO = BigDecimal.valueOf(10.5);
    public static final Integer QUANTIDADE_PRODUTO = 5;

    @MockBean
    private ProdutoRepository produtoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCriarProduto() throws Exception {
        Produto produtoRequest = new Produto();
        produtoRequest.setNome(NOME_PRODUTO);
        produtoRequest.setValor(VALOR_PRODUTO);
        produtoRequest.setQuantidade(QUANTIDADE_PRODUTO);

        when(produtoRepository.save(any())).thenReturn(ProdutoEntity.builder().id(ID_PRODUTO).nome(NOME_PRODUTO).valor(VALOR_PRODUTO).quantidade(QUANTIDADE_PRODUTO).build());

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(produtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$.valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$.quantidade").value(QUANTIDADE_PRODUTO));

        verify(produtoRepository, times(1)).save(any());
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    public void deveConsultarTodos() throws Exception {

        when(produtoRepository.findAll()).thenReturn(List.of(
                ProdutoEntity.builder().id(ID_PRODUTO).nome(NOME_PRODUTO).valor(VALOR_PRODUTO).quantidade(QUANTIDADE_PRODUTO).build(),
                ProdutoEntity.builder().id(2L).nome(NOME_PRODUTO + " 2").valor(VALOR_PRODUTO).quantidade(QUANTIDADE_PRODUTO).build()));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$[1].nome").value(NOME_PRODUTO + " 2"));

        verify(produtoRepository, times(1)).findAll();
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    public void deveConsultarPorId() throws Exception {
        when(produtoRepository.findById(ID_PRODUTO)).thenReturn(Optional.of(ProdutoEntity.builder()
                .id(ID_PRODUTO)
                .nome(NOME_PRODUTO)
                .valor(VALOR_PRODUTO)
                .quantidade(QUANTIDADE_PRODUTO)
                .build()));

        mockMvc.perform(get("/produtos/" + ID_PRODUTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$.valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$.quantidade").value(QUANTIDADE_PRODUTO));

        verify(produtoRepository, times(1)).findById(ID_PRODUTO);
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    public void deveAlterarProduto() throws Exception {
        String nomeAlterado = "Produto Alterado";
        BigDecimal valorAlterado = BigDecimal.valueOf(50);
        int quantidadeAlterada = 30;

        Produto produtoRequest = new Produto();
        produtoRequest.setNome(nomeAlterado);
        produtoRequest.setValor(valorAlterado);
        produtoRequest.setQuantidade(quantidadeAlterada);

        when(produtoRepository.findById(ID_PRODUTO)).thenReturn(Optional.of(ProdutoEntity.builder().id(ID_PRODUTO).nome(NOME_PRODUTO).valor(VALOR_PRODUTO).quantidade(QUANTIDADE_PRODUTO).build()));
        when(produtoRepository.save(any())).thenReturn(ProdutoEntity.builder().id(ID_PRODUTO).nome(nomeAlterado).valor(valorAlterado).quantidade(quantidadeAlterada).build());

        mockMvc.perform(put("/produtos/" + ID_PRODUTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(produtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_PRODUTO))
                .andExpect(jsonPath("$.nome").value(nomeAlterado))
                .andExpect(jsonPath("$.valor").value(valorAlterado))
                .andExpect(jsonPath("$.quantidade").value(quantidadeAlterada));

        verify(produtoRepository, times(1)).findById(ID_PRODUTO);
        verify(produtoRepository, times(1)).save(any());
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    public void deveDeletarProduto() throws Exception {
        when(produtoRepository.findById(ID_PRODUTO)).thenReturn(Optional.of(ProdutoEntity.builder().id(ID_PRODUTO).nome(NOME_PRODUTO).valor(VALOR_PRODUTO).quantidade(QUANTIDADE_PRODUTO).build()));
        doNothing().when(produtoRepository).deleteById(ID_PRODUTO);

        mockMvc.perform(delete("/produtos/" + ID_PRODUTO))
                .andExpect(status().isOk());

        verify(produtoRepository, times(1)).findById(ID_PRODUTO);
        verify(produtoRepository, times(1)).deleteById(ID_PRODUTO);
        verifyNoMoreInteractions(produtoRepository);
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
