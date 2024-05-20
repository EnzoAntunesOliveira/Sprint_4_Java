package org.example.repositories;

import org.example.entities.Endereco;
import org.example.entities._BaseEntity;

import java.util.List;

public class _BaseRepositoryImpl <T extends _BaseEntity> implements _BaseRepository<T>{
    @Override
    public Endereco Create(T entity) {

        return null;
    }

    @Override
    public boolean DeleteById(int id) {
        return false;
    }

    @Override
    public T ReadById(int id) {
        return null;
    }

    @Override
    public List<T> ReadAll() {
        return null;
    }

    @Override
    public boolean UpdateById(T entity, int id) {
        return false;
    }
}


