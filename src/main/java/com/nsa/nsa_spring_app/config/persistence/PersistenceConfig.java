package com.nsa.nsa_spring_app.config.persistence;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration()
public class PersistenceConfig {
    @Primary
    @Bean(name = "masterInstance")
    @ConfigurationProperties("spring.datasource.masterdb")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slaveOneInstance")
    @ConfigurationProperties("spring.datasource.slave-1-db")
    public DataSource slaveOneDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Primary
    @Bean(name = {"transactionManager", "masterTransactionManager"})
    public PlatformTransactionManager masterTransactionManager(@Qualifier("masterInstance") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "slaveTransactionManager")
    public PlatformTransactionManager slaveTransactionManager(@Qualifier("slaveOneInstance") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
