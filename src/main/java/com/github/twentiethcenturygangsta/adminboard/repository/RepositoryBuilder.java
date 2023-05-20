package com.github.twentiethcenturygangsta.adminboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public class RepositoryBuilder {

    public static <T, ID> JpaRepository<T, ID> getJpaRepositoryInstance(Object object, T domain, ID id ) {
        return (JpaRepository<T, ID>) object;
    }

    public static <T, ID> CrudRepository<T, ID> getCrudRepositoryInstance(Object object, T domain, ID id) {
        return (CrudRepository<T, ID>) object;
    }

    public static <T, ID> ListCrudRepository<T, ID> getListCrudRepositoryInstance(Object object, T domain, ID id) {
        return (ListCrudRepository<T, ID>) object;
    }

    public static <T, ID> ListPagingAndSortingRepository<T, ID> getListPagingAndSortingRepositoryInstance(Object object, T domain, ID id) {
        return (ListPagingAndSortingRepository<T, ID>) object;
    }

    public static <T, ID> PagingAndSortingRepository<T, ID> getPagingAndSortingRepositoryInstance(Object object, T domain, ID id) {
        return (PagingAndSortingRepository<T, ID>) object;
    }
}
