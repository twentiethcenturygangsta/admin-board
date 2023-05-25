package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.client.AdminBoardClient;
import com.github.twentiethcenturygangsta.adminboard.client.EntityClient;
import com.github.twentiethcenturygangsta.adminboard.repository.AdminBoardUserRepository;
import com.github.twentiethcenturygangsta.adminboard.repository.RepositoryClient;
import com.github.twentiethcenturygangsta.adminboard.view.AdminBoardViewActionController;
import com.github.twentiethcenturygangsta.adminboard.view.AdminBoardViewController;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.github.twentiethcenturygangsta.adminboard")
@EnableJpaRepositories("com.github.twentiethcenturygangsta.adminboard")
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
    public AdminBoardFactory adminBoardFactory(RepositoryClient repositoryClient,  AdminBoardClient adminBoardClient, EntityClient entityClient) {
        return new AdminBoardFactory(repositoryClient, adminBoardClient, entityClient);
    }

    @Bean
    public AdminBoardViewController adminBoardviewController(AdminBoardFactory adminBoardFactory, AdminBoardServiceFactory adminBoardServiceFactory, AdminBoardLoginService adminBoardLoginService) {
        return new AdminBoardViewController(adminBoardFactory, adminBoardServiceFactory, adminBoardLoginService);
    }

    @Bean
    public AdminBoardViewActionController adminBoardViewActionController(AdminBoardServiceFactory adminBoardServiceFactory, AdminBoardLoginService adminBoardLoginService) {
        return new AdminBoardViewActionController(adminBoardServiceFactory, adminBoardLoginService);
    }

    @Bean
    public AdminBoardServiceFactory adminBoardServiceFactory(AdminBoardUserRepository adminBoardUserRepository, AdminBoardClient adminBoardClient) {
        AdminBoardServiceFactory adminBoardServiceFactory = new AdminBoardServiceFactory(adminBoardUserRepository, adminBoardClient);
        adminBoardServiceFactory.createSuperAdminBoardUser();
        return adminBoardServiceFactory;
    }

    @Bean
    public AdminBoardLoginService adminBoardLoginService(AdminBoardUserRepository adminBoardUserRepository) {
        return new AdminBoardLoginService(adminBoardUserRepository);
    }

    @Bean
    public MvcConfiguration mvcConfiguration() {
        return new MvcConfiguration();
    }

    @Bean
    public AdminBoardWebConfig adminBoardWebConfig() {
        return new AdminBoardWebConfig();
    }
}
