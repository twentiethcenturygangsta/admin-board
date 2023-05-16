package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.annotation.registrar.AdminBoardRegistrar;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryInfo;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;

import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Component
public class AdminBoardRepositoryBuilder {
    private final ApplicationContext applicationContext;
    private final Map<String, RepositoryInfo> repositories = new HashMap<>();

    public AdminBoardRepositoryBuilder(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

// domain : metadata.getDomainType()
// repository id type : metadata.getIdType()
// repository : metadata.getRepositoryInterface()
// repository interfaces : metadata.getRepositoryInterface().getInterfaces()
    public void registerRepositories() {

        List<Class<?>> repositories = new ArrayList<>();
        List<String> basePackages = AdminBoardRegistrar.getBasePackages();

        for(String basePackage : basePackages) {
            repositories.addAll(new Reflections(basePackage).getTypesAnnotatedWith(Repository.class));
        }
        for (Class<?> object : repositories) {
            DefaultRepositoryMetadata metadata = new DefaultRepositoryMetadata(object);

            RepositoryInfo repositoryInfo = setRepository(metadata);
            this.repositories.put(metadata.getDomainType().getSimpleName(), repositoryInfo);
        }
    }

    public Map<String, RepositoryInfo> getRepositories() {
        return repositories;
    }

    private RepositoryInfo setRepository(DefaultRepositoryMetadata metadata) {
        Repositories repositoriesInApplicationContext = new Repositories(applicationContext);
        Object repository = repositoriesInApplicationContext.getRepositoryFor(metadata.getDomainType()).orElseThrow(() -> new RuntimeException("Not exist Repository"));

        List<Class<?>> repositoryInterfaces = Arrays.asList(metadata.getRepositoryInterface().getInterfaces());
        return RepositoryInfo.builder()
                    .repositoryInterfaces(repositoryInterfaces)
                    .repositoryObject(repository)
                    .metaData(metadata)
                    .build();
    }
}
