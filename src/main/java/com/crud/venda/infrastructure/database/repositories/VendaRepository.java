package com.crud.venda.infrastructure.database.repositories;

import com.crud.venda.infrastructure.database.entities.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<VendaEntity, Long> {

}
