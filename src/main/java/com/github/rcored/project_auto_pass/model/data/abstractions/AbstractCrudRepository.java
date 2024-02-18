package com.github.rcored.project_auto_pass.model.data.abstractions;

import com.github.rcored.project_auto_pass.model.data.exceptions.DataException;
import com.github.rcored.project_auto_pass.model.data.exceptions.EntityNotFoundException;

import java.util.Optional;

public interface AbstractCrudRepository<T,TypeId> {
    T create(T t) throws DataException;
    Optional<T> readById(TypeId id) throws EntityNotFoundException, DataException;
    T update(T newt) throws DataException;
    void deleteById(TypeId id);
    boolean existById(TypeId id);
}
