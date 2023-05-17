package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.client.EntityClient;
import com.github.twentiethcenturygangsta.adminboard.entity.EntityInfo;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminBoardFactory {
    private final RepositoryClient repositoryClient;
    private final EntityClient entityClient;

    public AdminBoardFactory(final RepositoryClient repositoryClient, final EntityClient entityClient) {
        this.repositoryClient = repositoryClient;
        this.entityClient = entityClient;
    }

    public  HashMap<String, List<EntityInfo>> getEntities() {
        HashMap<String, List<EntityInfo>> entitiesByGroup = new HashMap<>();

        for(EntityInfo entityInfo: entityClient.getEntities()) {
            if(entitiesByGroup.containsKey(entityInfo.getGroup())) {
                List<EntityInfo> entityInfos = entitiesByGroup.get(entityInfo.getGroup());
                entityInfos.add(entityInfo);
                entitiesByGroup.put(entityInfo.getGroup(), entityInfos);
            } else {
                entitiesByGroup.put(entityInfo.getGroup(), List.of(entityInfo));
            }
        }
        return entitiesByGroup;
    }
}
