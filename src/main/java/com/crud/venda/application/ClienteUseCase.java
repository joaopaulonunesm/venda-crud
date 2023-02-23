package com.crud.venda.application;

import com.crud.venda.application.exceptions.ApplicationException;
import com.crud.venda.application.exceptions.ApplicationMessage;
import com.crud.venda.domain.Cliente;
import com.crud.venda.domain.gateways.ClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteUseCase {

    private final ClienteGateway clienteGateway;

    public Cliente criar(Cliente cliente) {
        return clienteGateway.salvar(cliente);
    }

    public Cliente alterar(Long id, Cliente cliente) {
        consultarPorId(id);
        cliente.setId(id);
        return clienteGateway.salvar(cliente);
    }

    public List<Cliente> consultarTodos() {
        return clienteGateway.buscarTodos();
    }

    public Cliente consultarPorId(Long id) {
        return clienteGateway.buscarPorId(id).orElseThrow(() -> new ApplicationException(ApplicationMessage.CLIENTE_NAO_EXISTENTE, id));
    }

    public void deletar(Long id) {
        consultarPorId(id);
        clienteGateway.deletarPorId(id);
    }
}
