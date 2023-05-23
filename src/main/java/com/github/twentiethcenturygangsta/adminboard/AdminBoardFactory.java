package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.client.AdminBoardClient;
import com.github.twentiethcenturygangsta.adminboard.client.AdminBoardInfo;
import com.github.twentiethcenturygangsta.adminboard.client.EntityClient;
import com.github.twentiethcenturygangsta.adminboard.entity.EntityInfo;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryBuilder;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryClient;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Service
public class AdminBoardFactory {
    private final RepositoryClient repositoryClient;
    private final EntityClient entityClient;
    private final AdminBoardClient adminBoardClient;


    public AdminBoardFactory(final RepositoryClient repositoryClient, final AdminBoardClient adminBoardClient, final EntityClient entityClient) {
        this.repositoryClient = repositoryClient;
        this.entityClient = entityClient;
        this.adminBoardClient = adminBoardClient;
    }

    public  HashMap<String, ArrayList<EntityInfo>> getEntitiesByGroup() {
        HashMap<String, ArrayList<EntityInfo>> entitiesByGroup = new HashMap<>();

        for(EntityInfo entityInfo: entityClient.getEntities()) {
            if(entitiesByGroup.containsKey(entityInfo.getGroup())) {
                ArrayList<EntityInfo> entityInfos = entitiesByGroup.get(entityInfo.getGroup());
                entityInfos.add(entityInfo);
                entitiesByGroup.put(entityInfo.getGroup(), entityInfos);
            } else {
                entitiesByGroup.put(entityInfo.getGroup(), new ArrayList<>(List.of(entityInfo)));
            }
        }
        return entitiesByGroup;
    }

    public List<EntityInfo> getEntities() {
        return entityClient.getEntities();
    }
    public EntityInfo getEntity(String entityName){
        return entityClient.getEntity(entityName);
    }

    public HashMap<String, String> getAdminBoardInfo() {
        HashMap<String, String> adminBoardInfoTable = new HashMap<>();
        AdminBoardInfo adminBoardInfo = adminBoardClient.getAdminBoardInfo();
        adminBoardInfoTable.put("title", adminBoardInfo.getTitle());
        adminBoardInfoTable.put("description", adminBoardInfo.getDescription());
        adminBoardInfoTable.put("license", adminBoardInfo.getLicense());
        adminBoardInfoTable.put("licenseUrl", adminBoardInfo.getLicenseUrl());
        adminBoardInfoTable.put("version", adminBoardInfo.getVersion());
        return adminBoardInfoTable;
    }

    public Page<?> getObjects(String entityName, Pageable pageable) {
        RepositoryInfo repositoryInfo = repositoryClient.getRepository(entityName);
        Object repositoryObject = repositoryInfo.getRepositoryObject();

        RepositoryBuilder<?, ?> repositoryBuilder = RepositoryBuilder.forObject(repositoryObject, repositoryInfo.getDomain(), repositoryInfo.getIdType());

        PagingAndSortingRepository<?, ?> repository = repositoryBuilder.build(PagingAndSortingRepository.class);
        return repository.findAll(pageable);
    }

    public Optional<Object> getObject(String entityName, Long entityObjectId) {
        RepositoryInfo repositoryInfo = repositoryClient.getRepository(entityName);
        Object repositoryObject = repositoryInfo.getRepositoryObject();

        RepositoryBuilder<Object, Object> repositoryBuilder = RepositoryBuilder.forObject(repositoryObject, repositoryInfo.getDomain(), repositoryInfo.getIdType());

        CrudRepository<Object, Object> repository = repositoryBuilder.build(CrudRepository.class);
        return repository.findById(entityObjectId);
    }

    public Object getFieldMappingValue(Object root, String fieldName ) {
        try {
            Field field = root.getClass().getDeclaredField( fieldName );
            Method getter = root.getClass().getDeclaredMethod(
                    (field.getType().equals( boolean.class ) ? "is" : "get")
                            + field.getName().substring(0, 1).toUpperCase( Locale.ROOT)
                            + field.getName().substring(1)
            );

            return getter.invoke(root);
        } catch (Exception e) {
            // log exception
        }
        return null;
    }
}
