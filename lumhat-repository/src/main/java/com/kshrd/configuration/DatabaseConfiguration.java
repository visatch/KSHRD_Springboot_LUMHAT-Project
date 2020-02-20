package com.kshrd.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.kshrd.repository")
@PropertySource("classpath:application.properties")
public class DatabaseConfiguration {
    @Value("${spring.datasource.driver-class-name}")
    String className;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Bean
    @Qualifier("dataSource")
    public DataSource productionDataSource() {

//        String className = "org.postgresql.Driver";
//        String url = "jdbc:postgresql://35.240.141.73:5432/";
//        String username = "lumhat";
//        String password = "lumhat!@#";
        DriverManagerDataSource db = new DriverManagerDataSource();
        db.setDriverClassName(className);
        db.setUrl(url);
        db.setUsername(username);
        db.setPassword(password);
        return db;
    }
}
