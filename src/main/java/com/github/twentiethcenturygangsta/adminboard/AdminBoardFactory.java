package com.github.twentiethcenturygangsta.adminboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.twentiethcenturygangsta.adminboard.client.AdminBoardClient;
import com.github.twentiethcenturygangsta.adminboard.client.AdminBoardInfo;
import com.github.twentiethcenturygangsta.adminboard.client.EntityClient;
import com.github.twentiethcenturygangsta.adminboard.entity.ColumnInfo;
import com.github.twentiethcenturygangsta.adminboard.entity.EntityInfo;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryBuilder;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryClient;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryInfo;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.data.domain.*;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Service
public class AdminBoardFactory {
    private final RepositoryClient repositoryClient;
    private final EntityClient entityClient;
    private final AdminBoardClient adminBoardClient;
    private final ObjectMapper objectMapper;


    public AdminBoardFactory(final RepositoryClient repositoryClient, final AdminBoardClient adminBoardClient, final EntityClient entityClient, final ObjectMapper objectMapper) {
        this.repositoryClient = repositoryClient;
        this.entityClient = entityClient;
        this.adminBoardClient = adminBoardClient;
        this.objectMapper = objectMapper;
    }

    public Map<String, List<EntityInfo>> getGroupEntities() {
        Map<String, List<EntityInfo>> entitiesByGroup = new HashMap<>();
        List<EntityInfo> entityInfos = entityClient.getEntities();

        for (EntityInfo entityInfo : entityInfos) {
            String group = entityInfo.getGroup();
            List<EntityInfo> groupEntities = entitiesByGroup.getOrDefault(group, new ArrayList<>());
            groupEntities.add(entityInfo);
            entitiesByGroup.put(group, groupEntities);
        }
        return entitiesByGroup;
    }

    public List<EntityInfo> getEntities() {
        return entityClient.getEntities();
    }

    public EntityInfo getEntity(String entityName) {
        return entityClient.getEntity(entityName);
    }

    public Map<String, List<?>> getEnumClass() {
        Map<String, List<?>> enums = new HashMap<>();
        for(Map.Entry<String, Class<?>> enumClass : entityClient.getEnumClasses().entrySet()) {
            String key = enumClass.getKey();
            Class<?> value = enumClass.getValue();
            Object[] enumValues = value.getEnumConstants();
            if (enumValues != null) {
                enums.put(key, Arrays.asList(enumValues));
            }
        }
        return enums;
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

    public void createObject(String entityName, HashMap<String, Object> data) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<EntityInfo> entityInfos = entityClient.getEntities();

        Map<String, Object> object = new HashMap<>();
        for(Map.Entry<String, Object> value : data.entrySet()) {
            String fieldName = value.getKey();
            Object fieldValue = value.getValue();
            if (entityInfos.stream().anyMatch(entityInfo -> fieldName.equals(entityInfo.getName()))) {
                String newKey = "";
                EntityInfo entityInfo = entityClient.getEntity(entityName);
                RepositoryInfo mappedRepositoryInfo = repositoryClient.getRepository(fieldName);
                Object mappedRepositoryObject = mappedRepositoryInfo.getRepositoryObject();
                RepositoryBuilder<Object, Object> mappedRepositoryObjectBuilder = RepositoryBuilder.forObject(mappedRepositoryObject, mappedRepositoryInfo.getDomain(), mappedRepositoryInfo.getIdType());
                CrudRepository<Object, Object> mappedRepository = mappedRepositoryObjectBuilder.build(CrudRepository.class);
                Optional<Object> instance = mappedRepository.findById(fieldValue);
                for(ColumnInfo columnInfo: entityInfo.getColumns()) {
                    if(columnInfo.getType().equals(fieldName)) {
                        newKey = columnInfo.getName();
                    }
                }
                if(instance.isPresent()) {
                    object.put(newKey, instance.get());
                }
            } else {
                object.put(fieldName, fieldValue);
            }
        }

        log.info("createObject = {}", object);

        RepositoryInfo repositoryInfo = repositoryClient.getRepository(entityName);
        Object repositoryObject = repositoryInfo.getRepositoryObject();
        Class<?> entityClass = repositoryInfo.getDomain();
        RepositoryBuilder<Object, Object> repositoryBuilder = RepositoryBuilder.forObject(repositoryObject, repositoryInfo.getDomain(), repositoryInfo.getIdType());
        CrudRepository<Object, Object> repository = repositoryBuilder.build(CrudRepository.class);
        try {
            Constructor<?> constructor = entityClass.getDeclaredConstructor();
            constructor.setAccessible(true); // 접근 제한자 설정
            Object instance = constructor.newInstance();
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                Field field = entityClass.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                if(field.getType() == Long.class) {
                    field.set(instance, Long.parseLong((String) entry.getValue()));
                } else if (field.getType() == int.class) {
                    field.set(instance, Integer.parseInt((String) entry.getValue()));
                } else if (field.getType() == String.class) {
                    field.set(instance, entry.getValue());
                } else if (field.getType() == Boolean.class) {
                    field.set(instance, Boolean.parseBoolean((String) entry.getValue()));
                } else if(field.getType().isEnum()) {
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), (String) entry.getValue());
                    field.set(instance, enumValue);
                }
                else {
                    field.set(instance, entry.getValue());
//                    Map<String, List<?>> enums = getEnumClass();
//                    enums.get(field.getType().getSimpleName());
                }
            }
            repository.save(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Object instance = mapper.convertValue(object, entityClass);
//        Object instance = mapper.convertValue(object, entityClass);

//        repository.save(instance);
//        return repository.save(instance);
    }

    public Page<?> getObjects(String entityName, String keyword, String type, Pageable pageable) {
        RepositoryInfo repositoryInfo = repositoryClient.getRepository(entityName);
        Object repositoryObject = repositoryInfo.getRepositoryObject();
        if ("ALL".equals(keyword) || "ALL".equals(type)) {
            RepositoryBuilder<?, ?> repositoryBuilder = RepositoryBuilder.forObject(repositoryObject, repositoryInfo.getDomain(), repositoryInfo.getIdType());
            PagingAndSortingRepository<?, ?> repository = repositoryBuilder.build(PagingAndSortingRepository.class);
            return repository.findAll(pageable);
        }
        return getSearchObjects(entityName, type, keyword, pageable.getPageNumber(), pageable.getPageSize());

    }

    public <T> Page<T> getSearchObjects(String entityName, String searchType, String keyword, int pageIndex, int pageSize) {
        RepositoryInfo repositoryInfo = repositoryClient.getRepository(entityName);
        Object repositoryObject = repositoryInfo.getRepositoryObject();
        PagingAndSortingRepository<?, ?> repository = (PagingAndSortingRepository<?, ?>) repositoryObject;
        Sort sort = Sort.by(Sort.Direction.ASC, "id"); // 정렬 기준 필드와 방향 설정
        Iterable<?> allEntities = repository.findAll(sort);
        List<T> filteredEntities = new ArrayList<>();
        for (Object entity : allEntities) {
            if (containsIgnoreCase(getEntityValue(entity, searchType), keyword)) {
                T dto = createDtoFromEntity(entity, repositoryInfo.getDomain());
                filteredEntities.add(dto);
            }
        }

        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, filteredEntities.size());
        List<T> pageEntities = filteredEntities.subList(start, end);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return new PageImpl<>(pageEntities, pageable, filteredEntities.size());
    }

    private <T> T createDtoFromEntity(Object entity, Class<?> dtoClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Map<String, Object> object = new HashMap<>();

        try {

//            String json = objectMapper.writeValueAsString(entity);
//            T dto = objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(dtoClass));
//            log.info("dto = {}", dto);

           for (Field dtoField : dtoClass.getDeclaredFields()) {
                if (!isAssociationField(dtoField)) {
                    Field entityField = entity.getClass().getDeclaredField(dtoField.getName());
                    entityField.setAccessible(true);
                    Object value = entityField.get(entity);
                    object.put(dtoField.getName(), value);
                }
            }
            T dto = (T) objectMapper.convertValue(object, dtoClass);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isAssociationField(Field field) {
        return field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class) || field.isAnnotationPresent(OneToOne.class);
    }

    private Object getEntityFieldValue(Object entity, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(entity);
    }

    private String getEntityValue(Object entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(entity);
            return String.valueOf(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean containsIgnoreCase(String text, String keyword) {
        if (text == null || keyword == null) {
            return false;
        }
        return text.toLowerCase().contains(keyword.toLowerCase());
    }

    public Optional<Object> getObject(String entityName, Long entityObjectId) {
        RepositoryInfo repositoryInfo = repositoryClient.getRepository(entityName);
        Object repositoryObject = repositoryInfo.getRepositoryObject();

        RepositoryBuilder<Object, Object> repositoryBuilder = RepositoryBuilder.forObject(repositoryObject, repositoryInfo.getDomain(), repositoryInfo.getIdType());

        CrudRepository<Object, Object> repository = repositoryBuilder.build(CrudRepository.class);
        return repository.findById(entityObjectId);
    }

    public Object getFieldMappingValue(Object root, String fieldName) {

        try {
            Object unwrappedRoot = unwrapProxy(root);
//            Object unwrappedRoot = root;
            Field field = unwrappedRoot.getClass().getDeclaredField(fieldName);
            Method getter = unwrappedRoot.getClass().getDeclaredMethod((field.getType().equals(boolean.class) ? "is" : "get") + field.getName().substring(0, 1).toUpperCase(Locale.ROOT) + field.getName().substring(1));
//            log.info("unwrappedRoot = {} {} {} {}", unwrappedRoot, unwrappedRoot.getClass(), unwrappedRoot.getClass().getSimpleName(), field);
//            log.info("object = {}", getter.invoke(unwrappedRoot));
            return getter.invoke(unwrappedRoot);
        } catch (Exception e) {
            log.error("error = {}", root);
            e.printStackTrace();
        }
        return null;
    }

    private Object unwrapProxy(Object object) {
        if (object instanceof HibernateProxy) {
            try {
                return ((HibernateProxy) object).getHibernateLazyInitializer().getImplementation();
            } catch (Exception e) {
                log.error("unwrapProxy = {}", e.getMessage());
            }
        }
        return object;
    }
}
