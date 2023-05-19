package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.client.AdminBoardClient;
import com.github.twentiethcenturygangsta.adminboard.client.AdminBoardInfo;
import com.github.twentiethcenturygangsta.adminboard.client.EntityClient;
import com.github.twentiethcenturygangsta.adminboard.entity.EntityInfo;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
