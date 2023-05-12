package com.github.twentiethcenturygangsta.adminboard;

import com.github.twentiethcenturygangsta.adminboard.client.EntityClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AdminBoardAutoConfiguration {

    @Bean
    public EntityClient entityClient() {
        EntityClient entityClient = new EntityClient();
        entityClient.registerEntities();
        return entityClient;
    }
}
