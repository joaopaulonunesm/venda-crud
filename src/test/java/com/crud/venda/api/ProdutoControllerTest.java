package com.crud.venda.api;

import com.crud.venda.domain.Produto;
import com.crud.venda.infrastructure.database.repositories.ProdutoRepository;
import com.crud.venda.utils.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.crud.venda.utils.DataUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProdutoControllerTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCriarProduto() throws Exception {
        Produto produtoRequest = DataUtils.getProduto();

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(produtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$.valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$.quantidade").value(QUANTIDADE_PRODUTO));
    }

    @Test
    public void deveConsultarTodos() throws Exception {
        produtoRepository.save(DataUtils.getProdutoEntity());
        produtoRepository.save(DataUtils.getProdutoEntity2());

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
    }

    @Test
    public void deveConsultarPorId() throws Exception {
        produtoRepository.save(DataUtils.getProdutoEntity());

        mockMvc.perform(get("/produtos/" + ID_PRODUTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID_PRODUTO))
                .andExpect(jsonPath("$.nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$.valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$.quantidade").value(QUANTIDADE_PRODUTO));
    }

    @Test
    public void deveAlterarProduto() throws Exception {
        produtoRepository.save(DataUtils.getProdutoEntity());

        Produto produtoRequest = new Produto();
        produtoRequest.setNome(NOME_PRODUTO_2);
        produtoRequest.setValor(VALOR_PRODUTO_2);
        produtoRequest.setQuantidade(QUANTIDADE_PRODUTO_2);

        mockMvc.perform(put("/produtos/" + ID_PRODUTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(produtoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_PRODUTO))
                .andExpect(jsonPath("$.nome").value(NOME_PRODUTO_2))
                .andExpect(jsonPath("$.valor").value(VALOR_PRODUTO_2))
                .andExpect(jsonPath("$.quantidade").value(QUANTIDADE_PRODUTO_2));
    }

    @Test
    public void deveDeletarProduto() throws Exception {
        produtoRepository.save(DataUtils.getProdutoEntity());

        assertFalse(produtoRepository.findById(ID_PRODUTO).isEmpty());

        mockMvc.perform(delete("/produtos/" + ID_PRODUTO))
                .andExpect(status().isOk());

        assertTrue(produtoRepository.findById(ID_PRODUTO).isEmpty());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
