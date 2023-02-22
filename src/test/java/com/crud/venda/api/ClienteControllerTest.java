package com.crud.venda.api;

import com.crud.venda.domain.Cliente;
import com.crud.venda.infrastructure.database.repositories.ClienteRepository;
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
public class ClienteControllerTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCriarCliente() throws Exception {
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(DataUtils.getCliente())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(NOME_CLIENTE))
                .andExpect(jsonPath("$.cpf").value(CPF_CLIENTE));
    }

    @Test
    public void deveConsultarTodos() throws Exception {
        clienteRepository.save(getClienteEntity());
        clienteRepository.save(getClienteEntity2());

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(ID_CLIENTE))
                .andExpect(jsonPath("$[0].nome").value(NOME_CLIENTE))
                .andExpect(jsonPath("$[0].cpf").value(CPF_CLIENTE))
                .andExpect(jsonPath("$[1].id").value(ID_CLIENTE_2))
                .andExpect(jsonPath("$[1].nome").value(NOME_CLIENTE_2))
                .andExpect(jsonPath("$[1].cpf").value(CPF_CLIENTE_2));
    }

    @Test
    public void deveConsultarPorId() throws Exception {
        clienteRepository.save(getClienteEntity());

        mockMvc.perform(get("/clientes/" + ID_CLIENTE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID_CLIENTE))
                .andExpect(jsonPath("$.nome").value(NOME_CLIENTE))
                .andExpect(jsonPath("$.cpf").value(CPF_CLIENTE));
    }

    @Test
    public void deveAlterarCliente() throws Exception {
        clienteRepository.save(getClienteEntity());

        Cliente clienteRequest = new Cliente();
        clienteRequest.setNome(NOME_CLIENTE_2);
        clienteRequest.setCpf(CPF_CLIENTE_2);

        mockMvc.perform(put("/clientes/" + ID_CLIENTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_CLIENTE))
                .andExpect(jsonPath("$.nome").value(NOME_CLIENTE_2))
                .andExpect(jsonPath("$.cpf").value(CPF_CLIENTE_2));
    }

    @Test
    public void deveDeletarCliente() throws Exception {
        clienteRepository.save(getClienteEntity());

        assertFalse(clienteRepository.findById(ID_CLIENTE).isEmpty());

        mockMvc.perform(delete("/clientes/" + ID_CLIENTE))
                .andExpect(status().isOk());

        assertTrue(clienteRepository.findById(ID_CLIENTE).isEmpty());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
