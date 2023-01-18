package com.crud.venda.repositories;

import com.crud.venda.models.entity.ClienteEntity;
import com.crud.venda.models.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
