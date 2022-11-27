package luongdev.switchconfig.tenancy.datasource;

import luongdev.switchconfig.tenancy.Domain;
import luongdev.switchconfig.tenancy.Domains;
import org.flywaydb.core.Flyway;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DomainConnectionProvider
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements HibernatePropertiesCustomizer {

    private final Map<String, DataSource> dataSources = new TreeMap<>();

    private final Domains domainRepo;
    private final DomainIdentifierResolver domainIdentifierResolver;

    @Value("${database.host:localhost}")
    private String dbHost;

    @Value("${database.port:5432}")
    private int dbPort;

    @Value("${database.username:postgres}")
    private String dbUser;

    @Value("${database.password:}")
    private String dbPassword;

    @Value("${database.migrations:db/migrations}")
    private String migrations;

    private final Flyway flyway;

    public DomainConnectionProvider(
            Flyway flyway,
            DataSource dataSource,
            Domains domainRepo,
            DomainIdentifierResolver domainIdentifierResolver) {
        this.domainRepo = domainRepo;
        this.domainIdentifierResolver = domainIdentifierResolver;
        this.flyway = flyway;
        this.dataSources.put("public", dataSource);
    }


    @Override
    protected DataSource selectAnyDataSource() {
        if (dataSources.isEmpty()) {
            var domains = domainRepo.findAll();
            for (var domain : domains) dataSources.put(domain.getDomain(), domain.getDataSource());
        }

        return this.dataSources.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        var domainName = domainIdentifierResolver.resolveCurrentTenantIdentifier();
        if (!this.dataSources.containsKey(domainName)) {
            var domainOpt = domainRepo.findById(domainName);
            if (domainOpt.isEmpty())
                throw new DataSourceLookupFailureException("Cannot found dataSource for domain [" + domainName + "]");

            this.dataSources.put(domainName, initializeDataSource(domainOpt.get()));
        }

        return this.dataSources.get(domainName);
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return selectAnyDataSource().getConnection();
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }

    public DataSource initializeDataSource(Domain domain) {
        Connection connection;
        Statement statement = null;
        try {
            var dbName = domain.getDomain().replaceAll("\\.", "_");
            connection = DriverManager.getConnection(String.format("jdbc:postgresql://%s:%s/", dbHost, dbPort), dbUser, dbPassword);
            statement = connection.createStatement();
            statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = '" + dbName + "'");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count <= 0) statement.executeUpdate("CREATE DATABASE " + dbName + "");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        var ds = domain.getDataSource();
        var flyway = Flyway.configure()
                .configuration(this.flyway.getConfiguration())
                .dataSource(ds)
                .load();

        flyway.migrate();
        this.dataSources.put(domain.getDomain(), ds);

        return ds;
    }
}
