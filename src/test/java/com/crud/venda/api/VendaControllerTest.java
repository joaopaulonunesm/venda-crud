package com.crud.venda.api;

import com.crud.venda.domain.Venda;
import com.crud.venda.infrastructure.database.repositories.ClienteRepository;
import com.crud.venda.infrastructure.database.repositories.ProdutoRepository;
import com.crud.venda.infrastructure.database.repositories.VendaRepository;
import com.crud.venda.utils.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void before() {
        clienteRepository.save(getClienteEntity());
        clienteRepository.save(getClienteEntity2());
        produtoRepository.save(getProdutoEntity());
        produtoRepository.save(getProdutoEntity2());
    }

    @Test
    public void deveCriarVenda() throws Exception {

        Venda vendaRequest = DataUtils.getVenda();

        mockMvc.perform(post("/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID_VENDA))
                .andExpect(jsonPath("$.cliente.id").value(vendaRequest.getCliente().getId()))
                .andExpect(jsonPath("$.cliente.nome").value(vendaRequest.getCliente().getNome()))
                .andExpect(jsonPath("$.cliente.cpf").value(vendaRequest.getCliente().getCpf()))
                .andExpect(jsonPath("$.cliente.endereco").value(vendaRequest.getCliente().getEndereco()))
                .andExpect(jsonPath("$.produtos", hasSize(vendaRequest.getProdutos().size())))
                .andExpect(jsonPath("$.produtos[0].id").value(vendaRequest.getProdutos().get(0).getId()))
                .andExpect(jsonPath("$.produtos[0].valor").value(vendaRequest.getProdutos().get(0).getValor()))
                .andExpect(jsonPath("$.produtos[0].nome").value(vendaRequest.getProdutos().get(0).getNome()))
                .andExpect(jsonPath("$.produtos[0].quantidade").value(vendaRequest.getProdutos().get(0).getQuantidade()))
                .andExpect(jsonPath("$.produtos[1].id").value(vendaRequest.getProdutos().get(1).getId()))
                .andExpect(jsonPath("$.produtos[1].valor").value(vendaRequest.getProdutos().get(1).getValor()))
                .andExpect(jsonPath("$.produtos[1].nome").value(vendaRequest.getProdutos().get(1).getNome()))
                .andExpect(jsonPath("$.produtos[1].quantidade").value(vendaRequest.getProdutos().get(1).getQuantidade()))
                .andExpect(jsonPath("$.desconto").value(vendaRequest.getDesconto()))
                .andExpect(jsonPath("$.valorTotal").value(vendaRequest.getProdutos().get(0).getValor().add(vendaRequest.getProdutos().get(1).getValor())))
                .andExpect(jsonPath("$.valorFinal").value(vendaRequest.getProdutos().get(0).getValor().add(vendaRequest.getProdutos().get(1).getValor())));
    }

    @Test
    public void deveConsultarTodos() throws Exception {
        vendaRepository.save(getVendaEntity());
        vendaRepository.save(getVendaEntity2());

        mockMvc.perform(get("/vendas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(ID_VENDA))
                .andExpect(jsonPath("$[0].cliente.id").value(ID_CLIENTE))
                .andExpect(jsonPath("$[0].cliente.nome").value(NOME_CLIENTE))
                .andExpect(jsonPath("$[0].cliente.cpf").value(CPF_CLIENTE))
                .andExpect(jsonPath("$[0].cliente.endereco").value(ENDERECO_CLIENTE))
                .andExpect(jsonPath("$[0].produtos", hasSize(2)))
                .andExpect(jsonPath("$[0].produtos[0].id").value(ID_PRODUTO))
                .andExpect(jsonPath("$[0].produtos[0].valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$[0].produtos[0].nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$[0].produtos[0].quantidade").value(QUANTIDADE_PRODUTO))
                .andExpect(jsonPath("$[0].produtos[1].id").value(ID_PRODUTO_2))
                .andExpect(jsonPath("$[0].produtos[1].valor").value(VALOR_PRODUTO_2))
                .andExpect(jsonPath("$[0].produtos[1].nome").value(NOME_PRODUTO_2))
                .andExpect(jsonPath("$[0].produtos[1].quantidade").value(QUANTIDADE_PRODUTO_2))
                .andExpect(jsonPath("$[0].desconto").value(0D))
                .andExpect(jsonPath("$[0].valorTotal").value(VALOR_PRODUTO.add(VALOR_PRODUTO_2)))
                .andExpect(jsonPath("$[0].valorFinal").value(VALOR_PRODUTO.add(VALOR_PRODUTO_2)))
                .andExpect(jsonPath("$[1].id").value(ID_VENDA_2))
                .andExpect(jsonPath("$[1].cliente.id").value(ID_CLIENTE_2))
                .andExpect(jsonPath("$[1].cliente.nome").value(NOME_CLIENTE_2))
                .andExpect(jsonPath("$[1].cliente.cpf").value(CPF_CLIENTE_2))
                .andExpect(jsonPath("$[1].cliente.endereco").value(ENDERECO_CLIENTE))
                .andExpect(jsonPath("$[1].produtos", hasSize(2)))
                .andExpect(jsonPath("$[1].produtos[0].id").value(ID_PRODUTO))
                .andExpect(jsonPath("$[1].produtos[0].valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$[1].produtos[0].nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$[1].produtos[0].quantidade").value(QUANTIDADE_PRODUTO))
                .andExpect(jsonPath("$[1].produtos[1].id").value(ID_PRODUTO_2))
                .andExpect(jsonPath("$[1].produtos[1].valor").value(VALOR_PRODUTO_2))
                .andExpect(jsonPath("$[1].produtos[1].nome").value(NOME_PRODUTO_2))
                .andExpect(jsonPath("$[1].produtos[1].quantidade").value(QUANTIDADE_PRODUTO_2))
                .andExpect(jsonPath("$[1].desconto").value(0D))
                .andExpect(jsonPath("$[1].valorTotal").value(VALOR_PRODUTO.add(VALOR_PRODUTO_2)))
                .andExpect(jsonPath("$[1].valorFinal").value(VALOR_PRODUTO.add(VALOR_PRODUTO_2)));
    }

    @Test
    public void deveConsultarPorId() throws Exception {
        vendaRepository.save(getVendaEntity());

        mockMvc.perform(get("/vendas/" + ID_VENDA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID_VENDA))
                .andExpect(jsonPath("$.cliente.id").value(ID_CLIENTE))
                .andExpect(jsonPath("$.cliente.nome").value(NOME_CLIENTE))
                .andExpect(jsonPath("$.cliente.cpf").value(CPF_CLIENTE))
                .andExpect(jsonPath("$.cliente.endereco").value(ENDERECO_CLIENTE))
                .andExpect(jsonPath("$.produtos", hasSize(2)))
                .andExpect(jsonPath("$.produtos[0].id").value(ID_PRODUTO))
                .andExpect(jsonPath("$.produtos[0].valor").value(VALOR_PRODUTO))
                .andExpect(jsonPath("$.produtos[0].nome").value(NOME_PRODUTO))
                .andExpect(jsonPath("$.produtos[0].quantidade").value(QUANTIDADE_PRODUTO))
                .andExpect(jsonPath("$.produtos[1].id").value(ID_PRODUTO_2))
                .andExpect(jsonPath("$.produtos[1].valor").value(VALOR_PRODUTO_2))
                .andExpect(jsonPath("$.produtos[1].nome").value(NOME_PRODUTO_2))
                .andExpect(jsonPath("$.produtos[1].quantidade").value(QUANTIDADE_PRODUTO_2))
                .andExpect(jsonPath("$.desconto").value(0D))
                .andExpect(jsonPath("$.valorTotal").value(VALOR_PRODUTO.add(VALOR_PRODUTO_2)))
                .andExpect(jsonPath("$.valorFinal").value(VALOR_PRODUTO.add(VALOR_PRODUTO_2)));
    }

    @Test
    public void deveAlterarVenda() throws Exception {
        vendaRepository.save(getVendaEntity());

        Venda vendaRequest = new Venda();
        vendaRequest.setCliente(getCliente2());
        vendaRequest.setProdutos(List.of(getProduto2()));

        mockMvc.perform(put("/vendas/" + ID_VENDA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID_VENDA))
                .andExpect(jsonPath("$.cliente.id").value(vendaRequest.getCliente().getId()))
                .andExpect(jsonPath("$.cliente.nome").value(vendaRequest.getCliente().getNome()))
                .andExpect(jsonPath("$.cliente.cpf").value(vendaRequest.getCliente().getCpf()))
                .andExpect(jsonPath("$.cliente.endereco").value(vendaRequest.getCliente().getEndereco()))
                .andExpect(jsonPath("$.produtos", hasSize(vendaRequest.getProdutos().size())))
                .andExpect(jsonPath("$.produtos[0].id").value(vendaRequest.getProdutos().get(0).getId()))
                .andExpect(jsonPath("$.produtos[0].valor").value(vendaRequest.getProdutos().get(0).getValor()))
                .andExpect(jsonPath("$.produtos[0].nome").value(vendaRequest.getProdutos().get(0).getNome()))
                .andExpect(jsonPath("$.produtos[0].quantidade").value(vendaRequest.getProdutos().get(0).getQuantidade()))
                .andExpect(jsonPath("$.desconto").value(vendaRequest.getDesconto()))
                .andExpect(jsonPath("$.valorTotal").value(vendaRequest.getProdutos().get(0).getValor()))
                .andExpect(jsonPath("$.valorFinal").value(vendaRequest.getProdutos().get(0).getValor()));
    }

    @Test
    public void deveDeletarVenda() throws Exception {
        vendaRepository.save(getVendaEntity());

        assertFalse(vendaRepository.findById(ID_VENDA).isEmpty());

        mockMvc.perform(delete("/vendas/" + ID_VENDA)).andExpect(status().isOk());

        assertTrue(vendaRepository.findById(ID_VENDA).isEmpty());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}