package com.javainuse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;


@Configuration
    public class EntityManagerFactoriesConfiguration {
        @Autowired
        private DataSource dataSource;


        public LocalContainerEntityManagerFactoryBean emf() {
            LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
            emf.setDataSource(dataSource);
            emf.setPackagesToScan(new String[] {"your.package"});
            emf.setJpaVendorAdapter(
                    new HibernateJpaVendorAdapter());
            return emf;
        }
    }

