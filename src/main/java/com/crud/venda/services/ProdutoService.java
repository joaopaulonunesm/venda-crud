package com.crud.venda.services;

import com.crud.venda.models.converter.EntityToDTOConverter;
import com.crud.venda.models.dto.Produto;
import com.crud.venda.models.entity.ProdutoEntity;
import com.crud.venda.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final EntityToDTOConverter<ProdutoEntity, Produto> converter;

    public Produto criar(Produto Produto) {
        return converter.toDomain(produtoRepository.save(converter.toEntity(Produto)));
    }

    public Produto alterar(Long id, Produto Produto) {
        Produto.setId(id);
        return converter.toDomain(produtoRepository.save(converter.toEntity(Produto)));
    }

    public List<Produto> consultarTodos() {
        return converter.toDomains(produtoRepository.findAll());
    }

    public Produto consultarPorId(Long id) {
        return converter.toDomain(produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não existe")));
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
