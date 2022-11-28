package luongdev.switchconfig.tenancy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        transactionManagerRef = "publicTransactionManager",
//        entityManagerFactoryRef = "publicEntityManagerFactory"
//)
@PropertySource(value = {"classpath:public.properties"})
public abstract class AbstractPublicDomainConfiguration {

    @Value("${database.host:localhost}")
    private String dbHost;

    @Value("${database.port:5432}")
    private int dbPort;

    @Value("${database.username:postgres}")
    private String dbUser;

    @Value("${database.password:}")
    private String dbPassword;

    @Value("${database.name}")
    private String dbName;

    private String[] scanPackages = {Domain.class.getPackage().getName()};

    protected void addScanPackages(String... scanPackages) {
        var result = Arrays.copyOf(this.scanPackages, this.scanPackages.length + scanPackages.length);
        System.arraycopy(scanPackages, 0, result, this.scanPackages.length, scanPackages.length);

        this.scanPackages = result;
    }

    @Bean
    @Primary
    public DataSource datasource() {
        var ds = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName))
                .username(dbUser)
                .password(dbPassword)
                .build();

        initDataSourceSchema(ds);

        return ds;
    }

    @Primary
    @Bean(name = "publicEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory(DataSource dataSource) {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(scanPackages);
        em.setPersistenceUnitName("DEFAULT-PERSISTENCE-UNIT");

        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Primary
    @Bean(name = "publicTransactionManager")
    public JpaTransactionManager masterTransactionManager(
            @Qualifier("publicEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        var properties = new Properties();
        properties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, false);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");

        return properties;
    }

    private void initDataSourceSchema(DataSource ds) {
        try (Statement statement = ds.getConnection().createStatement()) {
            statement.executeQuery("SELECT count(*) FROM information_schema.schemata WHERE schema_name = 'cluster'");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count <= 0) statement.executeUpdate("CREATE SCHEMA \"cluster\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
