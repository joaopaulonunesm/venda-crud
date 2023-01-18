package com.crud.venda.services;

import com.crud.venda.models.converter.EntityToDTOConverter;
import com.crud.venda.models.dto.Produto;
import com.crud.venda.models.dto.Venda;
import com.crud.venda.models.entity.VendaEntity;
import com.crud.venda.repositories.ClienteRepository;
import com.crud.venda.repositories.ProdutoRepository;
import com.crud.venda.repositories.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    private final EntityToDTOConverter<VendaEntity, Venda> converter;

    public Venda criar(Venda venda) {

        venda.setCliente(clienteService.consultarPorId(venda.getCliente().getId()));

        List<Produto> produtos = new ArrayList<>();
        venda.getProdutos().forEach(produto -> produtos.add(produtoService.consultarPorId(produto.getId())));
        venda.setProdutos(produtos);

        return converter.toDomain(vendaRepository.save(converter.toEntity(venda)));
    }

    public Venda alterar(Long id, Venda Venda) {
        Venda.setId(id);
        return converter.toDomain(vendaRepository.save(converter.toEntity(Venda)));
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
