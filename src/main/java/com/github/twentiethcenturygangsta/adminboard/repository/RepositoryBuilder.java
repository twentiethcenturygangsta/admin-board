package com.github.twentiethcenturygangsta.adminboard.repository;

public class RepositoryBuilder<T, ID> {
    private final Object repositoryObject;
    private final T domainClass;
    private final ID idType;

    private RepositoryBuilder(Object repositoryObject, T domainClass, ID idType) {
        this.repositoryObject = repositoryObject;
        this.domainClass = domainClass;
        this.idType = idType;
    }

    public static <T, ID> RepositoryBuilder<T, ID> forObject(Object repositoryObject, T domainClass, ID idType) {
        return new RepositoryBuilder<>(repositoryObject, domainClass, idType);
    }

    public <R> R build(Class<R> repositoryType) {
        if (repositoryType.isInstance(repositoryObject)) {
            return repositoryType.cast(repositoryObject);
        } else {
            throw new IllegalArgumentException("Repository object is not an instance of " + repositoryType.getName());
        }
    }
}
