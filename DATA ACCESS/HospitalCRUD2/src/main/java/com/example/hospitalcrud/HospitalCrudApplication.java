package com.example.hospitalcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.hospitalcrud.dao.repositories")
@EntityScan(basePackages = "com.example.hospitalcrud.dao.model")
public class HospitalCrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalCrudApplication.class, args);
    }
}
