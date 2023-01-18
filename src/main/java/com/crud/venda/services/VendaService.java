package com.crud.venda.services;

import com.crud.venda.models.converter.Converter;
import com.crud.venda.models.dto.Venda;
import com.crud.venda.models.entity.VendaEntity;
import com.crud.venda.repositories.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    private final Converter<VendaEntity, Venda> converter;

    public Venda criar(Venda venda) {
        venda.setCliente(clienteService.consultarPorId(venda.getCliente().getId()));
        venda.setProdutos(venda.getProdutos().stream().map(produto -> produtoService.consultarPorId(produto.getId())).toList());
        return converter.toDomain(vendaRepository.save(converter.toEntity(venda)));
    }

    public Venda alterar(Long id, Venda venda) {
        venda.setId(id);
        return converter.toDomain(vendaRepository.save(converter.toEntity(venda)));
    }

    public List<Venda> consultarTodos() {
        return converter.toDomains(vendaRepository.findAll());
    }

    public Venda consultarPorId(Long id) {
        return converter.toDomain(vendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Venda não existe")));
    }

    public void deletar(Long id) {
        vendaRepository.deleteById(id);
    }
}
