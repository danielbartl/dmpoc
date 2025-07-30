package dev.jbaby.dmpoc.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
public class Config {

    /**
     * Defines the transaction manager for JPA (PostgreSQL).
     * It is marked as @Primary, making it the default choice for @Transactional annotations.
     *
     * @param entityManagerFactory The factory for creating JPA EntityManagers.
     * @return The JpaTransactionManager bean.
     */
    @Primary
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * Defines the transaction manager for MongoDB.
     * It has a unique name and can be used by specifying its qualifier.
     *
     * @param dbFactory The factory for creating MongoDB connections.
     * @return The MongoTransactionManager bean.
     */
    @Bean(name = "mongoTransactionManager")
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}
