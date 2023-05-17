package com.github.twentiethcenturygangsta.adminboard.repository;

import com.github.twentiethcenturygangsta.adminboard.utils.AdminBoardStringConvertUtil;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.*;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;

import java.util.List;

@Getter
public class RepositoryInfo {

    private final Class<?> repositoryType;
    private final Object repositoryObject;
    private final Object idType;
    private final String domainName;

    @Builder
    public RepositoryInfo(List<Class<?>> repositoryInterfaces, Object repositoryObject, DefaultRepositoryMetadata metaData) {
        this.repositoryType = getRepositoryType(repositoryInterfaces);
        this.repositoryObject = repositoryObject;
        this.idType = metaData.getIdType();
        this.domainName = getRepositoryDomainName(metaData);
    }

    private String getRepositoryDomainName(DefaultRepositoryMetadata metadata) {
        return AdminBoardStringConvertUtil.getFormattedTableName(metadata.getDomainType().getSimpleName());
    }

    private Class<?> getRepositoryType(List<Class<?>> repositoryInterfaces) {
        if (repositoryInterfaces.contains(JpaRepository.class)) {
            return JpaRepository.class;
        }
        if (repositoryInterfaces.contains(CrudRepository.class)) {
            return CrudRepository.class;
        }
        if (repositoryInterfaces.contains(ListCrudRepository.class)) {
            return ListCrudRepository.class;
        }
        if (repositoryInterfaces.contains(ListPagingAndSortingRepository.class)) {
            return ListPagingAndSortingRepository.class;
        }
        return PagingAndSortingRepository.class;
    }
}
