package com.ascending.training.repository;

public interface GeneralDao<T,ID> {
    T save(T t);
    T update(T t);
    T getBy(ID id);
}
