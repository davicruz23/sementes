package com.teste.sementes.repository;

import com.teste.sementes.domain.AbstractEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface IGenericRepository<E extends AbstractEntity> extends ListCrudRepository<E, Long> {
}
