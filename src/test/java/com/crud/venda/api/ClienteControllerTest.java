package com.crud.venda.api;

import com.crud.venda.domain.Cliente;
import com.crud.venda.infrastructure.database.entities.ClienteEntity;
import com.crud.venda.infrastructure.database.repositories.ClienteRepository;
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
public class ClienteControllerTest {

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveCriarCliente() throws Exception {
        when(clienteRepository.save(any())).thenReturn(DataUtils.getClienteEntity());

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(DataUtils.getCliente())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(NOME_CLIENTE))
                .andExpect(jsonPath("$.cpf").value(CPF_CLIENTE));

        verify(clienteRepository, times(1)).save(any());
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    public void deveConsultarTodos() throws Exception {

        when(clienteRepository.findAll()).thenReturn(DataUtils.getClientesEntity());

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(ID_CLIENTE))
                .andExpect(jsonPath("$[0].nome").value(NOME_CLIENTE))
                .andExpect(jsonPath("$[0].cpf").value(CPF_CLIENTE))
                .andExpect(jsonPath("$[1].id").value(ID_CLIENTE_2))
                .andExpect(jsonPath("$[1].nome").value(NOME_CLIENTE_2))
                .andExpect(jsonPath("$[1].cpf").value(CPF_CLIENTE_2));

        verify(clienteRepository, times(1)).findAll();
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    public void deveConsultarPorId() throws Exception {
        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(DataUtils.getClienteEntity()));

        mockMvc.perform(get("/clientes/" + ID_CLIENTE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID_CLIENTE))
                .andExpect(jsonPath("$.nome").value(NOME_CLIENTE))
                .andExpect(jsonPath("$.cpf").value(CPF_CLIENTE));

        verify(clienteRepository, times(1)).findById(ID_CLIENTE);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    public void deveAlterarCliente() throws Exception {
        Cliente clienteRequest = new Cliente();
        clienteRequest.setNome(NOME_CLIENTE_2);
        clienteRequest.setCpf(CPF_CLIENTE_2);

        ClienteEntity clienteEntityAlterado = ClienteEntity.builder().id(ID_CLIENTE).nome(clienteRequest.getNome()).cpf(clienteRequest.getCpf()).build();

        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(DataUtils.getClienteEntity()));
        when(clienteRepository.save(any())).thenReturn(clienteEntityAlterado);

        mockMvc.perform(put("/clientes/" + ID_CLIENTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_CLIENTE))
                .andExpect(jsonPath("$.nome").value(NOME_CLIENTE_2))
                .andExpect(jsonPath("$.cpf").value(CPF_CLIENTE_2));

        verify(clienteRepository, times(1)).findById(ID_CLIENTE);
        verify(clienteRepository, times(1)).save(any());
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    public void deveDeletarCliente() throws Exception {
        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(DataUtils.getClienteEntity()));
        doNothing().when(clienteRepository).deleteById(ID_CLIENTE);

        mockMvc.perform(delete("/clientes/" + ID_CLIENTE))
                .andExpect(status().isOk());

        verify(clienteRepository, times(1)).findById(ID_CLIENTE);
        verify(clienteRepository, times(1)).deleteById(ID_CLIENTE);
        verifyNoMoreInteractions(clienteRepository);
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
