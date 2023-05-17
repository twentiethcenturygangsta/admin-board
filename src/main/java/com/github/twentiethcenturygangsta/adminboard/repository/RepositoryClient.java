package com.github.twentiethcenturygangsta.adminboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public class RepositoryClient {

    public static JpaRepository<?, ?> getJpaRepositoryInstance(Object object) {
        return (JpaRepository<?, ?>) object;
    }

    public static CrudRepository<?, ?> getCrudRepositoryInstance(Object object) {
        return (CrudRepository<?, ?>) object;
    }

    public static ListCrudRepository<?, ?> getListCrudRepositoryInstance(Object object) {
        return (ListCrudRepository<?, ?>) object;
    }

    public static ListPagingAndSortingRepository<?, ?> getListPagingAndSortingRepositoryInstance(Object object) {
        return (ListPagingAndSortingRepository<?, ?>) object;
    }

    public static PagingAndSortingRepository<?, ?> getPagingAndSortingRepositoryInstance(Object object) {
        return (PagingAndSortingRepository<?, ?>) object;
    }
}
