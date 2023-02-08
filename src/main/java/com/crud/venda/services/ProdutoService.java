package com.crud.venda.services;

import com.crud.venda.models.converter.Converter;
import com.crud.venda.models.dto.Produto;
import com.crud.venda.models.entity.ProdutoEntity;
import com.crud.venda.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final Converter<ProdutoEntity, Produto> converter;

    public Produto criar(Produto produto) {
        return converter.toDomain(produtoRepository.save(converter.toEntity(produto)));
    }

    public Produto alterar(Long id, Produto produto) {
        consultarPorId(id);
        produto.setId(id);
        return converter.toDomain(produtoRepository.save(converter.toEntity(produto)));
    }

    public List<Produto> consultarTodos() {
        return converter.toDomains(produtoRepository.findAll());
    }

    public Produto consultarPorId(Long id) {
        return converter.toDomain(produtoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não existe")));
    }

    public void deletar(Long id) {
        consultarPorId(id);
        try {
            produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Produto não pode ser deletado pois já existe vendas associado a ele.");
        }
    }
}
