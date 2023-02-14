package com.crud.venda.api;

import com.crud.venda.domain.Produto;
import com.crud.venda.infrastructure.database.entities.ProdutoEntity;
import com.crud.venda.infrastructure.database.repositories.ProdutoRepository;
import com.crud.venda.utils.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.crud.venda.utils.DataUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @MockBean
    private ProdutoRepository produtoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCriarProduto() throws Exception {

        Produto produtoRequest = DataUtils.getProduto();

        when(produtoRepository.save(any())).thenReturn(DataUtils.getProdutoEntity());

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
        when(produtoRepository.findAll()).thenReturn(DataUtils.getProdutosEntity());

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(ID_PRODUTO))
                .andExpect(jsonPath("$[0].nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$[0].valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$[0].quantidade").value(QUANTIDADE_PRODUTO))
                .andExpect(jsonPath("$[1].id").value(ID_PRODUTO_2))
                .andExpect(jsonPath("$[1].nome").value(NOME_PRODUTO_2))
                .andExpect(jsonPath("$[1].valor").value(VALOR_PRODUTO_2))
                .andExpect(jsonPath("$[1].quantidade").value(QUANTIDADE_PRODUTO_2));

        verify(produtoRepository, times(1)).findAll();
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    public void deveConsultarPorId() throws Exception {
        when(produtoRepository.findById(ID_PRODUTO)).thenReturn(Optional.of(DataUtils.getProdutoEntity()));

        mockMvc.perform(get("/produtos/" + ID_PRODUTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID_PRODUTO))
                .andExpect(jsonPath("$.nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$.valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$.quantidade").value(QUANTIDADE_PRODUTO));

        verify(produtoRepository, times(1)).findById(ID_PRODUTO);
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    public void deveAlterarProduto() throws Exception {
        Produto produtoRequest = new Produto();
        produtoRequest.setNome(NOME_PRODUTO_2);
        produtoRequest.setValor(VALOR_PRODUTO_2);
        produtoRequest.setQuantidade(QUANTIDADE_PRODUTO_2);

        ProdutoEntity produtoEntityAlterado = ProdutoEntity.builder()
                .id(ID_PRODUTO)
                .nome(produtoRequest.getNome())
                .valor(produtoRequest.getValor())
                .quantidade(produtoRequest.getQuantidade())
                .build();

        when(produtoRepository.findById(ID_PRODUTO)).thenReturn(Optional.of(DataUtils.getProdutoEntity()));
        when(produtoRepository.save(any())).thenReturn(produtoEntityAlterado);

        mockMvc.perform(put("/produtos/" + ID_PRODUTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(produtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_PRODUTO))
                .andExpect(jsonPath("$.nome").value(NOME_PRODUTO_2))
                .andExpect(jsonPath("$.valor").value(VALOR_PRODUTO_2))
                .andExpect(jsonPath("$.quantidade").value(QUANTIDADE_PRODUTO_2));

        verify(produtoRepository, times(1)).findById(ID_PRODUTO);
        verify(produtoRepository, times(1)).save(any());
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    public void deveDeletarProduto() throws Exception {
        when(produtoRepository.findById(ID_PRODUTO)).thenReturn(Optional.of(DataUtils.getProdutoEntity()));
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
