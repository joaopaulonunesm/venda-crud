package com.crud.venda.infrastructure;

import com.crud.venda.infrastructure.database.entities.converter.Converter;
import com.crud.venda.infrastructure.database.entities.ProdutoEntity;
import com.crud.venda.infrastructure.database.repositories.ProdutoRepository;
import com.crud.venda.domain.Produto;
import com.crud.venda.domain.gateways.ProdutoGateway;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProdutoDataProvider implements ProdutoGateway {

    private final Converter<ProdutoEntity, Produto> converter;

    private final ProdutoRepository produtoRepository;

    @Override
    public Produto salvar(Produto produto) {
        return converter.toDomain(produtoRepository.save(converter.toEntity(produto)));
    }

    @Override
    public List<Produto> buscarTodos() {
        return converter.toDomains(produtoRepository.findAll());
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository
                .findById(id)
                .map(produto -> Optional.of(converter.toDomain(produto)))
                .orElse(Optional.empty());
    }

    @Override
    public void deletarPorId(Long id) {
        try {
            produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Produto não pode ser deletado pois já existe vendas associado a ele.");
        }
    }
}
