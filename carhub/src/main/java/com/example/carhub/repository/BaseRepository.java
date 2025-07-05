package com.example.carhub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import jakarta.persistence.EntityNotFoundException;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    
    default T create(T entity) {
        return save(entity);
    }

    default void updateById(ID id) {
        T existingEntity = findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        save(existingEntity);
    }

    default void deleteById(ID id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException("Entity not found");
        }
        
        deleteById(id);
    }

    default List<T> findAllEntities() {
        return findAll();
    }

    default Optional<T> getEntityById(ID id) {
        return findById(id);
    }
}
