package com.bca.opentemplate.springrestjpa.configuration.datasource;

import java.util.HashMap;

import javax.sql.DataSource;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.bca.opentemplate.springrestjpa.configuration.logging.BaseLogging;

@Configuration
@EnableJpaRepositories(basePackages = "com.bca.opentemplate.springrestjpa.repository", entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager")
@DependsOn("baseLogging")
public class DatasourceConfiguration extends BaseLogging {
    @Value("${application.localDev:true}")
    boolean localDev;

    @Value("${datasource.jndi.url}")
    String jndiUrl;

    @Value("${datasource.jdbc.url}")
    String jdbcUrl;

    @Value("${datasource.jdbc.username}")
    String jdbcUsername;

    @Value("${datasource.jdbc.password}")
    String jdbcPassword;

    @Value("${datasource.autoddl:none}")
    private String autoDdl;

    @Bean("entityManager")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.bca.opentemplate.springrestjpa.model.dao");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());

        properties.put("hibernate.hbm2ddl.auto", autoDdl);
        if (localDev) {
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");
        }
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean("datasource")
    public DataSource dataSource() {
        if (localDev) {
            transLog.info("Using JDBC Connection: {}", jdbcUrl);
            return DataSourceBuilder.create().url(jdbcUrl).username(jdbcUsername).password(jdbcPassword).build();
        }

        transLog.info("Using JNDI Connection: {}", jndiUrl);
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource(jndiUrl);
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManager().getObject());
        return jpaTransactionManager;
    }
}
