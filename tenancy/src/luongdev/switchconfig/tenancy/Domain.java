package luongdev.switchconfig.tenancy;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;

@Entity
@Table(name = "domains")
public class Domain {

    @Id
    @Column(name = "domain")
    private String domain;

    @Column(name = "db_host", nullable = false)
    private String dbHost;

    @Column(name = "db_port", nullable = false)
    private short dbPort;

    @Column(name = "db_name", nullable = false)
    private String dbName;

    @Column(name = "db_user", nullable = false)
    private String dbUser;

    @Column(name = "db_password")
    private String dbPassword;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    public Domain() {
        this.domain = "public";
        this.dbHost = "localhost";
        this.dbPort = 5432;
        this.dbUser = "postgres";
        this.dbName = "_public";
        this.enabled = true;
    }

    public Domain(String domain) {
        this();

        assert StringUtils.isNotEmpty(domain);
        this.domain = domain;
        this.dbName = domain.replaceAll("\\.", "_");
    }

    public String getJdbcUrl(String dbName) {
        var url = String.format("jdbc:postgresql://%s:%s", dbHost, dbPort);
        if (StringUtils.isEmpty(dbName)) return url;

        return String.format("%s/%s", url, dbName);
    }

    public DataSource getDataSource() {
        var ds = new HikariDataSource();
        ds.setJdbcUrl(getJdbcUrl(dbName));
        ds.setUsername(dbUser);
        ds.setPassword(dbPassword);
        ds.setConnectionInitSql("""

                """);

        ds.setConnectionTestQuery("select 1");
        ds.setPoolName(String.format("%s-%s-DSPOOL", this.domain, this.dbName));
        ds.setDriverClassName("org.postgresql.Driver");

        return ds;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public short getDbPort() {
        return dbPort;
    }

    public void setDbPort(short dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
