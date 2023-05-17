package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.client.EntityClient;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryClient;
import com.github.twentiethcenturygangsta.adminboard.view.AdminBoardViewController;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
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
    public RepositoryClient repositoryClient(ApplicationContext applicationContext) {
        RepositoryClient adminBoardRepositoryBuilder = new RepositoryClient(applicationContext);
        adminBoardRepositoryBuilder.registerRepositories();
        return adminBoardRepositoryBuilder;
    }

    @Bean
    public AdminBoardFactory adminBoardFactory(RepositoryClient repositoryClient, EntityClient entityClient) {
        return new AdminBoardFactory(repositoryClient, entityClient);
    }

    @Bean
    public AdminBoardViewController adminBoardviewController(AdminBoardFactory adminBoardFactory) {
        return new AdminBoardViewController(adminBoardFactory);
    }
}
