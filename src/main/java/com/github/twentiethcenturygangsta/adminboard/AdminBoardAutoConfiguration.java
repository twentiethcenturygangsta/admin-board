package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.client.EntityClient;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminBoardAutoConfiguration {

    @Bean
    public EntityClient entityClient() {
        EntityClient entityClient = new EntityClient();
        entityClient.registerEntities();
        return entityClient;
    }

    @Bean
    public DefaultListableBeanFactory defaultListableBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Bean
    public AdminBoardRepositoryBuilder adminBoardRepositoryBuilder() {
        AdminBoardRepositoryBuilder adminBoardRepositoryBuilder = new AdminBoardRepositoryBuilder(defaultListableBeanFactory());
        adminBoardRepositoryBuilder.registerRepositories();

        return adminBoardRepositoryBuilder;
    }
}
