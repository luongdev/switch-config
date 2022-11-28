package luongdev.switchconfig.domain;

import luongdev.switchconfig.tenancy.datasource.DomainConnectionProvider;
import luongdev.switchconfig.tenancy.datasource.DomainIdentifierResolver;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "luongdev.switchconfig.domain",
        transactionManagerRef = "domainTransactionManager",
        entityManagerFactoryRef = "domainEntityManagerFactory"
)
public class DomainJpaConfiguration {

    private final JpaProperties jpaProperties;

    public DomainJpaConfiguration(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Bean(name = "domainJpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "domainTransactionManager")
    public JpaTransactionManager transactionManager(
            @Qualifier("domainEntityManagerFactory") EntityManagerFactory tenantEntityManager) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(tenantEntityManager);

        return transactionManager;
    }

    @Bean(name = "domainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DomainConnectionProvider domainConnectionProvider,
            DomainIdentifierResolver domainIdentifierResolver) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setPackagesToScan(this.getClass().getPackageName());
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setPersistenceUnitName("DOMAIN-PERSISTENCE-UNIT");

        var properties = new HashMap<String, Object>();
        properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, domainConnectionProvider);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, domainIdentifierResolver);
        properties.put(Environment.SHOW_SQL, jpaProperties.isShowSql());
        properties.put(Environment.FORMAT_SQL, true);
        properties.put(Environment.HBM2DDL_AUTO, "update");
        properties.put(Environment.ORDER_INSERTS, "true");
        properties.put(Environment.ORDER_UPDATES, "true");

        em.setJpaPropertyMap(properties);

        return em;
    }

}
